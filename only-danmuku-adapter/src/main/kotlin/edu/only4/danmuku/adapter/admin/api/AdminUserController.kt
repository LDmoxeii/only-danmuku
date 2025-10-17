package edu.only4.danmuku.adapter.admin.api

import edu.only4.danmuku.adapter.admin.api.payload.AdminUserChangeStatus
import edu.only4.danmuku.adapter.admin.api.payload.AdminUserLoad
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员用户管理控制器
 */
@RestController
@RequestMapping("/admin/user")
@Validated
class AdminUserController {

    /**
     * 加载用户列表(分页)
     */
    @PostMapping("/loadUser")
    fun adminUserLoad(@RequestBody request: AdminUserLoad.Request): AdminUserLoad.Response {
        // TODO: 实现加载用户列表(分页)逻辑
        return AdminUserLoad.Response()
    }

    /**
     * 修改用户状态
     */
    @PostMapping("/changeStatus")
    fun adminUserChangeStatus(@RequestBody @Validated request: AdminUserChangeStatus.Request): AdminUserChangeStatus.Response {
        // TODO: 实现修改用户状态逻辑
        return AdminUserChangeStatus.Response()
    }

}
