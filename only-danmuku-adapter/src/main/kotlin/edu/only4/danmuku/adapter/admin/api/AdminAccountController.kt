package edu.only4.danmuku.adapter.admin.api

import edu.only4.danmuku.adapter.admin.api.payload.AdminAccountCheckCode
import edu.only4.danmuku.adapter.admin.api.payload.AdminAccountLogin
import edu.only4.danmuku.adapter.admin.api.payload.AdminAccountLogout
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * 管理员账户管理控制器
 */
@RestController
@RequestMapping("/admin/account")
@Validated
class AdminAccountController {

    /**
     * 获取管理员登录验证码
     */
    @GetMapping("/checkCode")
    fun adminAccountCheckCode(): AdminAccountCheckCode.Response {
        // TODO: 实现获取管理员登录验证码逻辑
        return AdminAccountCheckCode.Response()
    }

    /**
     * 管理员登录
     */
    @PostMapping("/login")
    fun adminAccountLogin(@RequestBody @Validated request: AdminAccountLogin.Request): AdminAccountLogin.Response {
        // TODO: 实现管理员登录逻辑
        return AdminAccountLogin.Response()
    }

    /**
     * 管理员登出
     */
    @PostMapping("/logout")
    fun adminAccountLogout(): AdminAccountLogout.Response {
        // TODO: 实现管理员登出逻辑
        return AdminAccountLogout.Response()
    }

}
