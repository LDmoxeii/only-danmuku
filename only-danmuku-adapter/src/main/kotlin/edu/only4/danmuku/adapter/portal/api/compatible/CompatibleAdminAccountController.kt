package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import cn.dev33.satoken.stp.StpUtil
import com.only.engine.entity.UserInfo
import com.only.engine.exception.KnownException
import com.only.engine.misc.ServletUtils
import com.only.engine.misc.ServletUtils.getClientIP
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.AdminAccountCheckCode
import edu.only4.danmuku.adapter.portal.api.payload.AdminAccountLogin
import edu.only4.danmuku.application.commands.user_behavior.RecordLoginLogCmd
import edu.only4.danmuku.application.distributed.clients.CaptchaGenCli
import edu.only4.danmuku.application.distributed.clients.CaptchaValidCli
import edu.only4.danmuku.application.queries.user.GetAccountInfoByEmailQry
import edu.only4.danmuku.domain._share.meta.user.SUser
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginResult
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginType
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/account")
class CompatibleAdminAccountController {

    @SaIgnore
    @PostMapping("/checkCode")
    fun adminAccountCheckCode(): AdminAccountCheckCode.Response {
        val result = Mediator.requests.send(CaptchaGenCli.Request("web-auth"))
        return AdminAccountCheckCode.Response(
            "data:image/png;base64,${result.byte}",
            result.captchaId
        )
    }

    @SaIgnore
    @PostMapping("/login")
    fun adminAccountLogin(
        @NotEmpty account: String,
        @NotEmpty(message = "密码不能为空") @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,18}$", message = "密码必须为8-18位字母和数字组合") password: String,
        @NotEmpty(message = "验证码不能为空") checkCodeKey: String,
        @NotEmpty(message = "验证码不能为空") checkCode: String,
    ): AdminAccountLogin.Response {
        val captchaValidationResult = Mediator.requests.send(CaptchaValidCli.Request(checkCodeKey, checkCode))
        require(captchaValidationResult.result) { "验证码错误" }

        val userAccount = Mediator.queries.send(
            GetAccountInfoByEmailQry.Request(
                email = account
            )
        )

        val user = Mediator.repositories
            .findOne(SUser.predicateById(userAccount.userId))
            .orElseThrow { KnownException("用户不存在") }

        val isPasswordCorrect = user.verifyPassword(password)
        if (!isPasswordCorrect) {
            val occurTime = System.currentTimeMillis() / 1000L
            val userAgent = ServletUtils.getRequest()?.getHeader("User-Agent")
            user.reportPasswordInputFailed(
                loginName = account,
                ip = getClientIP(),
                userAgent = userAgent,
                occurTime = occurTime,
                reason = "密码错误"
            )
            Mediator.uow.save()
            throw KnownException("密码错误")
        }

        LoginHelper.login(UserInfo(userAccount.userId, userAccount.type.code, userAccount.email))
        val token = StpUtil.getTokenValue()

        Mediator.commands.send(
            RecordLoginLogCmd.Request(
                userId = userAccount.userId,
                userType = userAccount.type,
                loginName = account,
                loginType = LoginType.PASSWORD,
                result = LoginResult.SUCCESS,
                ip = getClientIP()!!,
                userAgent = ServletUtils.getRequest()?.getHeader("User-Agent"),
                occurTime = System.currentTimeMillis() / 1000L
            )
        )

        return AdminAccountLogin.Response(
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
