package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import cn.dev33.satoken.stp.StpUtil
import com.only.engine.entity.UserInfo
import com.only.engine.enums.CaptchaChannel
import com.only.engine.exception.KnownException
import com.only.engine.misc.ServletUtils
import com.only.engine.misc.ServletUtils.getClientIP
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.account.CheckCode
import edu.only4.danmuku.adapter.portal.api.payload.account.AccountLogin
import edu.only4.danmuku.adapter.portal.api.payload.account.AccountRegister
import edu.only4.danmuku.adapter.portal.api.payload.account.GetUserCountInfo
import edu.only4.danmuku.adapter.portal.api.payload.account.ChangePassword
import edu.only4.danmuku.adapter.portal.api.payload.account.LoginBySms
import edu.only4.danmuku.adapter.portal.api.payload.account.SendSmsCode
import edu.only4.danmuku.application.commands.user.UpdateLoginInfoCmd
import edu.only4.danmuku.application.commands.user_behavior.RecordLoginLogCmd
import edu.only4.danmuku.application.distributed.clients.CaptchaGenCli
import edu.only4.danmuku.application.distributed.clients.CaptchaValidCli
import edu.only4.danmuku.application.queries.customer_profile.GetCustomerProfileQry
import edu.only4.danmuku.application.queries.user.GetAccountInfoByEmailQry
import edu.only4.danmuku.application.queries.user.GetUserByPhoneQry
import edu.only4.danmuku.application.queries.user.GetUserCountInfoQry
import edu.only4.danmuku.domain._share.meta.customer_profile.SCustomerProfile
import edu.only4.danmuku.domain._share.meta.user.SUser
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginResult
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class AccountController {

    @SaIgnore
    @PostMapping("/checkCode")
    fun checkCode(): CheckCode.Response {
        val result = Mediator.requests.send(CaptchaGenCli.Request("web-auth"))
        return CheckCode.Response(
            result.captchaId,
            "data:image/png;base64,${result.byte}",
        )
    }

    @SaIgnore
    @PostMapping("/register")
    fun register(@RequestBody @Validated request: AccountRegister.Request) {
        val captchaValidationResult = Mediator.requests.send(CaptchaValidCli.Request(request.checkCodeKey, request.checkCode))
        require(captchaValidationResult.result) { "验证码错误" }
        Mediator.cmd.send(AccountRegister.Converter.INSTANCE.toCmd(request))
    }

    @SaIgnore
    @PostMapping("/login")
    fun login(@RequestBody @Validated request: AccountLogin.Request): AccountLogin.Response {
        val captchaValidationResult = Mediator.requests.send(CaptchaValidCli.Request(request.checkCodeKey, request.checkCode))
        require(captchaValidationResult.result) { "验证码错误" }

        val userAccount = Mediator.queries.send(
            GetAccountInfoByEmailQry.Request(
                email = request.email
            )
        )

        val isPasswordCorrect = userAccount.password == request.password
        if (!isPasswordCorrect) {
            Mediator.commands.send(
                RecordLoginLogCmd.Request(
                    userId = userAccount.userId,
                    userType = userAccount.type,
                    loginName = request.email,
                    loginType = LoginType.PASSWORD,
                    result = LoginResult.FAILURE,
                    ip = getClientIP()!!,
                    userAgent = ServletUtils.getRequest()?.getHeader("User-Agent"),
                    reason = "密码错误",
                    occurTime = System.currentTimeMillis() / 1000L
                )
            )
            throw KnownException("密码错误")
        }

        val customerProfile = Mediator.queries.send(
            GetCustomerProfileQry.Request(
                customerId = userAccount.userId
            )
        )

        LoginHelper.login(
            UserInfo(
                userAccount.userId, userAccount.type.code, userAccount.nickName,
                extra = mapOf(
                    SCustomerProfile.props.avatar to (customerProfile.avatar ?: ""),
                    SUser.props.relatedId to (customerProfile.customerId)
                )
            )
        )

        Mediator.commands.send(
            RecordLoginLogCmd.Request(
                userId = userAccount.userId,
                userType = userAccount.type,
                loginName = request.email,
                loginType = LoginType.PASSWORD,
                result = LoginResult.SUCCESS,
                ip = getClientIP()!!,
                userAgent = ServletUtils.getRequest()?.getHeader("User-Agent"),
                occurTime = System.currentTimeMillis() / 1000L
            )
        )

        Mediator.commands.send(
            UpdateLoginInfoCmd.Request(
                userId = userAccount.userId,
                loginIp = getClientIP()!!,
            )
        )

        return AccountLogin.Response(
            userId = userAccount.userId,
            nickName = userAccount.nickName,
            avatar = customerProfile.avatar,
            expireAt = StpUtil.getTokenTimeout(),
            token = StpUtil.getTokenValue()
        )
    }

    @SaIgnore
    @PostMapping("/autoLogin")
    fun autoLogin() : AccountLogin.Response? {
        if (!LoginHelper.isLogin()) return null
        return AccountLogin.Response(
            userId = LoginHelper.getUserId()!!,
            nickName = LoginHelper.getUserInfo()!!.username,
            avatar = LoginHelper.getUserInfo()!!.extra[SCustomerProfile.props.avatar] as String,
            expireAt = StpUtil.getTokenTimeout(),
            token = StpUtil.getTokenValue()
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

    @PostMapping("/getUserCountInfo")
    fun getUserCountInfo(): GetUserCountInfo.Response {
        val currentUserId = LoginHelper.getUserId()!!

        val userCountInfo = Mediator.queries.send(
            GetUserCountInfoQry.Request(
                customerId = currentUserId
            )
        )

        return GetUserCountInfo.Converter.INSTANCE.fromApp(userCountInfo)
    }

    @SaIgnore
    @PostMapping("/sendSmsCode")
    fun sendSmsCode(@RequestBody @Validated request: SendSmsCode.Request): SendSmsCode.Response {
        val result = Mediator.requests.send(
            CaptchaGenCli.Request(
                bizType = request.scene,
                channel = CaptchaChannel.SMS,
                targets = listOf(request.phone),
                templateCode = null,
            )
        )
        return SendSmsCode.Response(
            captchaId = result.captchaId
        )
    }

    @SaIgnore
    @PostMapping("/loginBySms")
    fun loginBySms(@RequestBody @Validated request: LoginBySms.Request): LoginBySms.Response {
        // TODO：由于政策原因，暂时屏蔽短信验证码校验
//        val captchaValidationResult = Mediator.requests.send(
//            CaptchaValidCli.Request(request.captchaId, request.smsCode)
//        )
//        require(captchaValidationResult.result) { "短信验证码错误" }

        val userAccount = Mediator.queries.send(
            GetUserByPhoneQry.Request(
                phone = request.phone
            )
        )

        Mediator.commands.send(
            UpdateLoginInfoCmd.Request(
                userId = userAccount.userId,
                loginIp = getClientIP()!!,
            )
        )

        val customerProfile = Mediator.queries.send(
            GetCustomerProfileQry.Request(
                customerId = userAccount.userId
            )
        )

        LoginHelper.login(
            UserInfo(
                userAccount.userId, userAccount.type.code, userAccount.nickName,
                extra = mapOf(
                    SCustomerProfile.props.avatar to (customerProfile.avatar ?: ""),
                    SUser.props.relatedId to (customerProfile.customerId)
                )
            )
        )

        return LoginBySms.Response(
            userId = userAccount.userId,
            nickName = userAccount.nickName,
            avatar = customerProfile.avatar,
            token = StpUtil.getTokenValue(),
            expireAt = StpUtil.getTokenTimeout()
        )
    }

    @PostMapping("/changePassword")
    fun changePassword(@RequestBody @Validated request: ChangePassword.Request) {
        val currentUserId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            ChangePassword.Converter.INSTANCE.toCmd(request, currentUserId)
        )
    }
}
