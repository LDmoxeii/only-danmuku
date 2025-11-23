package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import cn.dev33.satoken.stp.StpUtil
import com.only.engine.entity.UserInfo
import com.only.engine.enums.CaptchaChannel
import com.only.engine.misc.ServletUtils.getClientIP
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.ChangePassword
import edu.only4.danmuku.adapter.portal.api.payload.LoginBySms
import edu.only4.danmuku.adapter.portal.api.payload.SendSmsCode
import edu.only4.danmuku.application.commands.user.UpdateLoginInfoCmd
import edu.only4.danmuku.application.distributed.clients.CaptchaGenCli
import edu.only4.danmuku.application.queries.customer_profile.GetCustomerProfileQry
import edu.only4.danmuku.application.queries.user.GetUserByPhoneQry
import edu.only4.danmuku.domain._share.meta.customer_profile.SCustomerProfile
import edu.only4.danmuku.domain._share.meta.user.SUser
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class AccountController {

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
    fun changePassword(@RequestBody @Validated payload: ChangePassword.Request) {
        val currentUserId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            ChangePassword.Converter.INSTANCE.toCmd(payload, currentUserId)
        )
    }
}
