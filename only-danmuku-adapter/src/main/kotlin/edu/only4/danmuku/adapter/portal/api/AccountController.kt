package edu.only4.danmuku.adapter.portal.api

import cn.dev33.satoken.annotation.SaIgnore
import cn.dev33.satoken.stp.StpUtil
import com.only.engine.entity.UserInfo
import com.only.engine.misc.ServletUtils.getClientIP
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.*
import edu.only4.danmuku.application.commands.user.RegisterAccountCmd
import edu.only4.danmuku.application.commands.user.UpdateLoginInfoCmd
import edu.only4.danmuku.application.distributed.clients.CaptchaGen
import edu.only4.danmuku.application.queries.user.GetUserCountInfoQry
import jakarta.servlet.http.HttpServletResponse
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account/v2")
@Validated
class AccountController {

    @SaIgnore
    @PostMapping("/checkCode")
    fun checkCode(): AccountCheckCode.Response {
        val result = Mediator.requests.send(CaptchaGen.Request("web-auth"))
        return AccountCheckCode.Response(
            result.captchaId,
            result.byte
        )
    }

    @SaIgnore
    @PostMapping("/register")
    fun register(@RequestBody @Validated request: AccountRegister.Request): AccountRegister.Response {
//        val captchaValidationResult = Mediator.requests.send(CaptchaValid.Request(request.checkCodeKey, request.checkCode))
//        require(captchaValidationResult.result) { "验证码错误" }

        Mediator.cmd.send(
            RegisterAccountCmd.Request(
                email = request.email,
                nickName = request.nickName,
                registerPassword = request.registerPassword
            )
        )
        return AccountRegister.Response()
    }

    @SaIgnore
    @PostMapping("/login")
    fun login(
        @RequestBody @Validated request: AccountLogin.Request,
    ): AccountLogin.Response {
//        val captchaValidationResult = Mediator.requests.send(CaptchaValid.Request(request.checkCodeKey, request.checkCode))
//        require(captchaValidationResult.result) { "验证码错误" }


        val userAccount = Mediator.commands.send(
            UpdateLoginInfoCmd.Request(
                email = request.email,
                password = request.password,
                loginIp = getClientIP()!!,
            )
        )

        LoginHelper.login(UserInfo(userAccount.userId, userAccount.userType.code, userAccount.username))

        return AccountLogin.Response(
            userId = userAccount.userId.toString(),
            nickName = userAccount.username,
            token = StpUtil.getTokenValue()
        )
    }

    @PostMapping("/autoLogin")
    fun autoLogin(response: HttpServletResponse): AccountAutoLogin.Response? {
        return AccountAutoLogin.Response()
    }

    /**
     * 用户登出
     *
     * 清除用户的登录状态和Cookie中的令牌信息
     *
     * @param response HTTP响应对象，用于清除Cookie
     * @return 登出成功响应对象
     */
    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): AccountLogout.Response {
        StpUtil.logout()
        return AccountLogout.Response()
    }

    @PostMapping("/getUserCountInfo")
    fun getUserCountInfo(): AccountUserCountInfo.Response {
        // 1. 从Token中获取当前用户ID
        val currentUserId = LoginHelper.getUserId()!!

        // 2. 查询用户统计信息
        val userCountInfo = Mediator.queries.send(
            GetUserCountInfoQry.Request(
                customerId = currentUserId
            )
        )

        // 3. 转换为响应对象
        return AccountUserCountInfo.Response(
            fansCount = userCountInfo.fansCount,
            currentCoinCount = userCountInfo.currentCoinCount,
            focusCount = userCountInfo.focusCount
        )
    }

}
