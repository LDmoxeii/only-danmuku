package edu.only4.danmuku.adapter.portal.api.admin

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.admin_user.GetUserPage
import edu.only4.danmuku.adapter.portal.api.payload.admin_user.ChangeStatus
import edu.only4.danmuku.application.commands.user.ChangeUserStatusCmd
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/user")
class AdminUserController {

    @PostMapping("/page")
    fun page(@RequestBody @Validated request: GetUserPage.Request): PageData<GetUserPage.Item> {
        val queryRequest = GetUserPage.Converter.INSTANCE.toQry(request).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { GetUserPage.Converter.INSTANCE.fromApp(it) },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/changeStatus")
    fun changeStatus(@RequestBody @Validated request: ChangeStatus.Request) {
        val status = request.status == 1

        Mediator.commands.send(
            ChangeUserStatusCmd.Request(
                userId = request.userId,
                status = status
            )
        )
    }

}
