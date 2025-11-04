package edu.only4.danmuku.adapter.portal.api.compatible

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.AdminUserLoad
import edu.only4.danmuku.application.commands.user.ChangeUserStatusCmd
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/user")
@Validated
class CompatibleAdminUserController {

    @PostMapping("/loadUser")
    fun getUserPage(request: AdminUserLoad.Request): PageData<AdminUserLoad.UserItem> {
        val queryRequest = AdminUserLoad.Converter.INSTANCE.toQry(request).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { AdminUserLoad.Converter.INSTANCE.fromApp(it) },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/changeStatus")
    fun changeUserStatus(userId: Long, status: Int) {
        val status = status == 1

        Mediator.commands.send(
            ChangeUserStatusCmd.Request(
                userId = userId,
                status = status
            )
        )
    }

}
