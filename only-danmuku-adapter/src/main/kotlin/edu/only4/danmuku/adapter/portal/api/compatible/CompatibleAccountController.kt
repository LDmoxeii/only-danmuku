package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import cn.dev33.satoken.stp.StpUtil
import com.only.engine.entity.UserInfo
import com.only.engine.enums.CaptchaChannel
import com.only.engine.exception.KnownException
import com.only.engine.misc.ServletUtils
import com.only.engine.misc.ServletUtils.getClientIP
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.*
import edu.only4.danmuku.application.commands.user.UpdateLoginInfoCmd
import edu.only4.danmuku.application.commands.user_behavior.RecordLoginLogCmd
import edu.only4.danmuku.application.distributed.clients.CaptchaGenCli
import edu.only4.danmuku.application.distributed.clients.CaptchaValidCli
import edu.only4.danmuku.application.queries.customer_profile.GetCustomerProfileQry
import edu.only4.danmuku.application.queries.user.GetAccountInfoByEmailQry
import edu.only4.danmuku.application.queries.user.GetUserCountInfoQry
import edu.only4.danmuku.application.queries.user.GetUserByPhoneQry
import edu.only4.danmuku.domain._share.meta.customer_profile.SCustomerProfile
import edu.only4.danmuku.domain._share.meta.user.SUser
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginResult
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginType
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
@Validated
class CompatibleAccountController {

    @SaIgnore
    @PostMapping("/checkCode")
    fun checkCode(): AccountCheckCode.Response {
        val result = Mediator.requests.send(CaptchaGenCli.Request("web-auth"))
        return AccountCheckCode.Response(
            result.captchaId,
            "data:image/png;base64,${result.byte}",
        )
    }

    @SaIgnore
    @PostMapping("/register")
    fun register(
        @NotEmpty(message = "邮箱不能为空") @Email(message = "邮箱格式不正确") @Size(max = 150, message = "邮箱长度不能超过150个字符") email: String,
        @NotEmpty(message = "昵称不能为空") @Size(max = 20, message = "昵称长度不能超过20个字符") nickName: String,
        @NotEmpty(message = "密码不能为空") @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,18}$", message = "密码必须为8-18位字母和数字组合") registerPassword: String,
        @NotEmpty(message = "验证码不能为空") checkCodeKey: String,
        @NotEmpty(message = "验证码不能为空") checkCode: String,
    ) {
        val captchaValidationResult = Mediator.requests.send(CaptchaValidCli.Request(checkCodeKey, checkCode))
        require(captchaValidationResult.result) { "验证码错误" }

        val registerPayload = AccountRegister.Request(
            email = email,
            nickName = nickName,
            registerPassword = registerPassword,
            checkCodeKey = checkCodeKey,
            checkCode = checkCode
        )
        Mediator.cmd.send(AccountRegister.Converter.INSTANCE.toCmd(registerPayload))
    }

    @SaIgnore
    @PostMapping("/login")
    fun login(
        @NotEmpty(message = "邮箱不能为空") @Email(message = "邮箱格式不正确") @Size(max = 150, message = "邮箱长度不能超过150个字符") email: String,
        @NotEmpty(message = "密码不能为空") @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,18}$", message = "密码必须为8-18位字母和数字组合") password: String,
        @NotEmpty(message = "验证码不能为空") checkCodeKey: String,
        @NotEmpty(message = "验证码不能为空") checkCode: String,
    ): AccountLogin.Response {
        val captchaValidationResult = Mediator.requests.send(CaptchaValidCli.Request(checkCodeKey, checkCode))
        require(captchaValidationResult.result) { "验证码错误" }

        val userAccount = Mediator.queries.send(
            GetAccountInfoByEmailQry.Request(
                email = email
            )
        )

        val isPasswordCorrect = userAccount.password == password
        if (!isPasswordCorrect) {
            Mediator.commands.send(
                RecordLoginLogCmd.Request(
                    userId = userAccount.userId,
                    userType = userAccount.type,
                    loginName = email,
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
                loginName = email,
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
    @PostMapping("/sendSmsCode")
    fun sendSmsCode(request: SendSmsCode.Request): SendSmsCode.Response {
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
    fun loginBySms(request: LoginBySms.Request): LoginBySms.Response {
        // TODO：由于政策原因，暂时屏蔽短信验证码校验
//        val captchaValidationResult = Mediator.requests.send(
//            CaptchaValidCli.Request(request.captchaId, request.smsCode)
//        )
//        require(captchaValidationResult.result) { "短信验证码错误" }

        val userAccount = try {
            Mediator.queries.send(
                GetUserByPhoneQry.Request(
                    phone = request.phone
                )
            )
        } catch (ex: Exception) {
            Mediator.commands.send(
                RecordLoginLogCmd.Request(
                    userId = null,
                    userType = UserType.valueOf(0),
                    loginName = request.phone,
                    loginType = LoginType.SMS_CODE,
                    result = LoginResult.FAILURE,
                    ip = getClientIP()!!,
                    userAgent = ServletUtils.getRequest()?.getHeader("User-Agent"),
                    reason = ex.message,
                    occurTime = System.currentTimeMillis() / 1000L
                )
            )
            throw ex
        }

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

        Mediator.commands.send(
            RecordLoginLogCmd.Request(
                userId = userAccount.userId,
                userType = userAccount.type,
                loginName = request.phone,
                loginType = LoginType.SMS_CODE,
                result = LoginResult.SUCCESS,
                ip = getClientIP()!!,
                userAgent = ServletUtils.getRequest()?.getHeader("User-Agent"),
                occurTime = System.currentTimeMillis() / 1000L
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
    fun changePassword(payload: ChangePassword.Request) {
        val currentUserId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            ChangePassword.Converter.INSTANCE.toCmd(payload, currentUserId)
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
    fun getUserCountInfo(): AccountUserCountInfo.Response {
        val currentUserId = LoginHelper.getUserId()!!

        val userCountInfo = Mediator.queries.send(
            GetUserCountInfoQry.Request(
                customerId = currentUserId
            )
        )

        return AccountUserCountInfo.Converter.INSTANCE.fromApp(userCountInfo)
    }

}
