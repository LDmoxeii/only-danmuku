package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import cn.dev33.satoken.stp.StpUtil
import com.only.engine.entity.UserInfo
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.AdminAccountCheckCode
import edu.only4.danmuku.adapter.portal.api.payload.AdminAccountLogin
import edu.only4.danmuku.application.distributed.clients.CaptchaGenCli
import edu.only4.danmuku.application.distributed.clients.CaptchaValidCli
import edu.only4.danmuku.application.queries.user.GetAccountInfoByEmailQry
import edu.only4.danmuku.domain.aggregates.user.User
import jakarta.validation.constraints.NotEmpty
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
        @NotEmpty password: String,
        @NotEmpty checkCode: String,
        @NotEmpty checkCodeKey: String,
    ): AdminAccountLogin.Response {
        val captchaValidationResult = Mediator.requests.send(CaptchaValidCli.Request(checkCodeKey, checkCode))
        require(captchaValidationResult.result) { "验证码错误" }
        val userAccount = Mediator.queries.send(
            GetAccountInfoByEmailQry.Request(
                email = account
            )
        )

        val isPasswordCorrect = User.isPasswordCorrect(userAccount.password, password)
        require(isPasswordCorrect) { "密码错误" }

        LoginHelper.login(UserInfo(userAccount.id, userAccount.type.code, userAccount.email))
        val token = StpUtil.getTokenValue()

        return AdminAccountLogin.Response(
            userId = userAccount.id.toString(),
            account = userAccount.email,
            nickName = userAccount.nickName,
            token = token
        )
    }
}
