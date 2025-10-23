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

/**
 * 用户账户管理控制器
 *
 * 处理用户注册、登录、登出等账户相关操作
 */
@RestController
@RequestMapping("/account")
@Validated
class AccountController {

    /**
     * 获取验证码
     *
     * 生成算术验证码并返回其Base64编码和唯一键
     *
     * @return 验证码响应对象
     */
    @SaIgnore
    @PostMapping("/checkCode")
    fun checkCode(): AccountCheckCode.Response {
        val result = Mediator.requests.send(CaptchaGen.Request("web-auth"))
        return AccountCheckCode.Response(
            result.captchaId,
            result.byte
        )
    }

    /**
     * 用户注册
     *
     * 验证邮箱、昵称、密码和验证码后完成用户注册
     *
     * @param request 用户注册请求参数
     * @return 注册成功响应对象
     */
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

    /**
     * 用户登录
     *
     * 验证邮箱、密码和验证码后完成用户登录，并将令牌保存到Cookie中
     *
     * @param request 用户登录请求参数
     * @param response HTTP响应对象，用于设置Cookie
     * @return 登录成功响应对象，包含用户信息和令牌
     */
    @SaIgnore
    @PostMapping("/login")
    fun login(
        @RequestBody @Validated request: AccountLogin.Request,
        response: HttpServletResponse,
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

    /**
     * 自动登录
     *
     * 检查当前用户令牌是否有效，若过期则重新生成令牌并保存到Cookie中
     *
     * @param response HTTP响应对象，用于更新Cookie
     * @return 自动登录成功响应对象，包含用户信息
     */
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

    /**
     * 获取用户统计信息
     *
     * 根据当前用户的ID获取其统计信息（粉丝数、关注数、当前硬币数等）
     *
     * @return 包含用户统计信息的响应对象
     */
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
