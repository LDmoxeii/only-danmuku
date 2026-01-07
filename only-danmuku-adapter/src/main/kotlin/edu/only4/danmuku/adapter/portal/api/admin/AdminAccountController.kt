package edu.only4.danmuku.adapter.portal.api.admin

import cn.dev33.satoken.annotation.SaIgnore
import cn.dev33.satoken.stp.StpUtil
import com.only.engine.entity.UserInfo
import com.only.engine.exception.KnownException
import com.only.engine.misc.ServletUtils
import com.only.engine.misc.ServletUtils.getClientIP
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.admin_account.CheckCode
import edu.only4.danmuku.adapter.portal.api.payload.admin_account.Login
import edu.only4.danmuku.application.commands.user_behavior.RecordLoginLogCmd
import edu.only4.danmuku.application.distributed.clients.CaptchaGenCli
import edu.only4.danmuku.application.distributed.clients.CaptchaValidCli
import edu.only4.danmuku.application.queries.user.GetAccountInfoByEmailQry
import edu.only4.danmuku.domain.aggregates.user.User
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginResult
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author zhenyu.jiang
 */
@RestController
@RequestMapping("/admin/account")
class AdminAccountController {

    @SaIgnore
    @PostMapping("/checkCode")
    fun checkCode(): CheckCode.Response {
        val result = Mediator.requests.send(CaptchaGenCli.Request("web-auth"))
        return CheckCode.Response(
            "data:image/png;base64,${result.byte}",
            result.captchaId
        )
    }

    @SaIgnore
    @PostMapping("/login")
    fun login(@RequestBody @Validated request: Login.Request): Login.Response {
        val captchaValidationResult =
            Mediator.requests.send(CaptchaValidCli.Request(request.checkCodeKey, request.checkCode))
        require(captchaValidationResult.result) { "验证码错误" }

        val userAccount = Mediator.queries.send(
            GetAccountInfoByEmailQry.Request(
                email = request.account
            )
        )

        val isPasswordCorrect = User.isPasswordCorrect(userAccount.password, request.password)
        if (!isPasswordCorrect) {
            val occurTime = System.currentTimeMillis() / 1000L
            val userAgent = ServletUtils.getRequest()?.getHeader("User-Agent")
            Mediator.commands.send(
                RecordLoginLogCmd.Request(
                    userId = userAccount.userId,
                    userType = userAccount.type,
                    loginName = request.account,
                    loginType = LoginType.PASSWORD,
                    result = LoginResult.FAILURE,
                    ip = getClientIP()!!,
                    userAgent = userAgent,
                    reason = "密码错误",
                    occurTime = occurTime
                )
            )
            throw KnownException("密码错误")
        }

        LoginHelper.login(UserInfo(userAccount.userId, userAccount.type.code, userAccount.email))
        val token = StpUtil.getTokenValue()

        Mediator.commands.send(
            RecordLoginLogCmd.Request(
                userId = userAccount.userId,
                userType = userAccount.type,
                loginName = request.account,
                loginType = LoginType.PASSWORD,
                result = LoginResult.SUCCESS,
                ip = getClientIP()!!,
                userAgent = ServletUtils.getRequest()?.getHeader("User-Agent"),
                occurTime = System.currentTimeMillis() / 1000L
            )
        )

        return Login.Response(
            userId = userAccount.userId,
            account = userAccount.email,
            nickName = userAccount.nickName,
            token = token
        )
    }

    @PostMapping("/logout")
    fun logout() {
        val userInfo = LoginHelper.getUserInfo()
        val userId = LoginHelper.getUserId()
        val ip = getClientIP().orEmpty()
        Mediator.commands.send(
            RecordLoginLogCmd.Request(
                userId = userId,
                userType = userInfo?.userType?.let { UserType.valueOf(it) } ?: UserType.valueOf(0),
                loginName = userInfo?.username ?: "",
                loginType = LoginType.LOGOUT,
                result = LoginResult.SUCCESS,
                ip = ip,
                userAgent = ServletUtils.getRequest()?.getHeader("User-Agent"),
                occurTime = System.currentTimeMillis() / 1000L
            )
        )
        StpUtil.logout()
    }
}
