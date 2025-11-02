package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import cn.dev33.satoken.stp.StpUtil
import com.only.engine.entity.UserInfo
import com.only.engine.misc.ServletUtils.getClientIP
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.AccountCheckCode
import edu.only4.danmuku.adapter.portal.api.payload.AccountLogin
import edu.only4.danmuku.adapter.portal.api.payload.AccountUserCountInfo
import edu.only4.danmuku.application.commands.user.RegisterAccountCmd
import edu.only4.danmuku.application.commands.user.UpdateLoginInfoCmd
import edu.only4.danmuku.application.distributed.clients.CaptchaGenCli
import edu.only4.danmuku.application.distributed.clients.CaptchaValidCli
import edu.only4.danmuku.application.queries.customer_profile.GetCustomerProfileQry
import edu.only4.danmuku.application.queries.user.GetUserCountInfoQry
import edu.only4.danmuku.domain._share.meta.customer_profile.SCustomerProfile
import edu.only4.danmuku.domain._share.meta.user.SUser
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

        Mediator.cmd.send(
            RegisterAccountCmd.Request(
                email = email,
                nickName = nickName,
                registerPassword = registerPassword
            )
        )
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


        val userAccount = Mediator.commands.send(
            UpdateLoginInfoCmd.Request(
                email = email,
                password = password,
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
                userAccount.userId, userAccount.userType.code, userAccount.username,
                extra = mapOf(
                    SCustomerProfile.props.avatar to (customerProfile.avatar ?: ""),
                    SUser.props.relatedId to (customerProfile.customerId)
                )
            )
        )

        return AccountLogin.Response(
            userId = userAccount.userId,
            nickName = userAccount.username,
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
    fun logout() = StpUtil.logout()

    @PostMapping("/getUserCountInfo")
    fun getUserCountInfo(): AccountUserCountInfo.Response {
        val currentUserId = LoginHelper.getUserId()!!

        val userCountInfo = Mediator.queries.send(
            GetUserCountInfoQry.Request(
                customerId = currentUserId
            )
        )

        return AccountUserCountInfo.Response(
            fansCount = userCountInfo.fansCount,
            currentCoinCount = userCountInfo.currentCoinCount,
            focusCount = userCountInfo.focusCount
        )
    }

}
