package edu.only4.danmuku.adapter.portal.api

import cn.dev33.satoken.stp.StpUtil
import com.only.engine.entity.UserInfo
import com.only.engine.redis.misc.RedisUtils
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api._share.constant.Constants
import edu.only4.danmuku.adapter.portal.api.payload.*
import edu.only4.danmuku.application.commands.user.RegisterAccountCmd
import edu.only4.danmuku.application.distributed.clients.CaptchaGen
import edu.only4.danmuku.application.distributed.clients.CaptchaValid
import edu.only4.danmuku.application.queries.user.GetAccountInfoByEmailQry
import edu.only4.danmuku.domain.aggregates.user.User
import jakarta.servlet.http.HttpServletResponse
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

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
    @GetMapping("/checkCode")
    fun checkCode(): AccountCheckCode.Response {
        val result = Mediator.requests.send(CaptchaGen.Request("auth"))
        RedisUtils.setCacheObject(Constants.REDIS_KEY_CHECK_CODE + result.captchaId, result.text)
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
    @PostMapping("/register")
    fun register(@RequestBody @Validated request: AccountRegister.Request): AccountRegister.Response {
        val validated = Mediator.requests.send(CaptchaValid.Request(request.checkCodeKey, request.checkCode))
        require(validated.result) { "验证失败" }
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
    @PostMapping("/login")
    fun login(
        @RequestBody @Validated request: AccountLogin.Request,
        response: HttpServletResponse,
    ): AccountLogin.Response {
        // TODO: 实现用户登录逻辑
        val validated = Mediator.requests.send(CaptchaValid.Request(request.checkCodeKey, request.checkCode))
        require(validated.result) { "验证失败" }
        // 2. 验证邮箱和密码
        val accountInfo = Mediator.queries.send(
            GetAccountInfoByEmailQry.Request(
                email = request.email
            )
        )

        val validatedPassword = User.validatePassword(accountInfo.password, request.password)
        require(validatedPassword) { "密码错误" }
        LoginHelper.login(UserInfo(accountInfo.id, accountInfo.type.code, accountInfo.email))
        val token = StpUtil.getTokenValue()
        return AccountLogin.Response(
            userId = accountInfo.id.toString(),
            nickName = accountInfo.nickName,
            token = token
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
        // TODO: 实现自动登录逻辑
        // 1. 从Cookie或Header获取Token
        // 2. 验证Token有效性
        // 3. 检查Token是否即将过期
        // 4. 如果即将过期则刷新Token
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
        // TODO: 实现用户登出逻辑
        // 1. 从Cookie或Header获取Token
        // 2. 从Redis中删除Token
        // 3. 清除Cookie
        return AccountLogout.Response()
    }

    /**
     * 获取用户统计信息
     *
     * 根据当前用户的ID获取其统计信息（粉丝数、关注数、获赞数等）
     *
     * @return 包含用户统计信息的响应对象
     */
    @GetMapping("/getUserCountInfo")
    fun getUserCountInfo(): AccountUserCountInfo.Response {
        // TODO: 实现获取用户统计信息逻辑
        // 1. 从Token中获取当前用户ID
        // 2. 查询用户统计信息
        return AccountUserCountInfo.Response()
    }

}
