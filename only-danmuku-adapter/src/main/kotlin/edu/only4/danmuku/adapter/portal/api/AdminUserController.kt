package edu.only4.danmuku.adapter.portal.api

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.AdminUserChangeStatus
import edu.only4.danmuku.adapter.portal.api.payload.AdminUserLoad
import edu.only4.danmuku.application.commands.user.DisableAccountCmd
import edu.only4.danmuku.application.commands.user.EnableAccountCmd
import edu.only4.danmuku.application.queries.user.GetUsersByStatusQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

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
    fun adminUserLoad(@RequestBody request: AdminUserLoad.Request): PageData<AdminUserLoad.UserItem> {
        // 调用查询获取用户分页列表
        val queryRequest = GetUsersByStatusQry.Request(
            nickNameFuzzy = request.nickNameFuzzy,
            status = request.status
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        // 转换为前端需要的格式
        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { user ->
                AdminUserLoad.UserItem(
                    userId = user.userId.toString(),
                    email = user.email,
                    nickName = user.nickName,
                    avatar = user.avatar,
                    sex = user.sex,
                    birthday = user.birthday,
                    school = user.school,
                    personalSignature = user.personalSignature,
                    status = user.status,
                    joinTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(user.joinTime),
                        ZoneId.systemDefault()
                    ),
                    lastLoginTime = user.lastLoginTime?.let {
                        LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(it),
                            ZoneId.systemDefault()
                        )
                    },
                    currentCoinCount = user.currentCoinCount,
                    theme = user.theme
                )
            },
            totalCount = queryResult.totalCount
        )
    }

    /**
     * 修改用户状态
     */
    @PostMapping("/changeStatus")
    fun adminUserChangeStatus(@RequestBody @Validated request: AdminUserChangeStatus.Request): AdminUserChangeStatus.Response {
        val userId = request.userId!!.toLong()
        val status = request.status!!

        // 根据状态调用不同的命令
        when (status) {
            0 -> {
                // 禁用账号
                Mediator.commands.send(
                    DisableAccountCmd.Request(userId = userId)
                )
            }
            1 -> {
                // 启用账号
                Mediator.commands.send(
                    EnableAccountCmd.Request(userId = userId)
                )
            }
            else -> throw KnownException("无效的状态值: $status")
        }

        return AdminUserChangeStatus.Response()
    }

}
