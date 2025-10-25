package edu.only4.danmuku.adapter.portal.api

import cn.dev33.satoken.annotation.SaIgnore
import cn.dev33.satoken.stp.StpUtil
import com.only.engine.entity.UserInfo
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.AdminAccountCheckCode
import edu.only4.danmuku.adapter.portal.api.payload.AdminAccountLogin
import edu.only4.danmuku.adapter.portal.api.payload.AdminAccountLogout
import edu.only4.danmuku.application.distributed.clients.CaptchaGen
import edu.only4.danmuku.application.queries.user.GetAccountInfoByEmailQry
import edu.only4.danmuku.domain.aggregates.user.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/account")
@Validated
class AdminAccountController {

    @SaIgnore
    @PostMapping("/checkCode")
    fun adminAccountCheckCode(): AdminAccountCheckCode.Response {
        val result = Mediator.requests.send(CaptchaGen.Request("web-auth"))
        return AdminAccountCheckCode.Response(
            result.byte.toString(),
            result.captchaId
        )
    }

    @SaIgnore
    @PostMapping("/login")
    fun adminAccountLogin(@RequestBody @Validated request: AdminAccountLogin.Request): AdminAccountLogin.Response {
        //        val captchaValidationResult = Mediator.requests.send(CaptchaValid.Request(request.checkCodeKey, request.checkCode))
//        require(captchaValidationResult.result) { "验证码错误" }

        val userAccount = Mediator.queries.send(
            GetAccountInfoByEmailQry.Request(
                email = request.account
            )
        )

        val isPasswordCorrect = User.isPasswordCorrect(userAccount.password, request.password)
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

    @PostMapping("/logout")
    fun adminAccountLogout(): AdminAccountLogout.Response {
        StpUtil.logout()
        return AdminAccountLogout.Response()
    }

}
