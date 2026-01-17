package edu.only4.danmuku.adapter.portal.api.admin

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.admin_user.ChangeStatus
import edu.only4.danmuku.adapter.portal.api.payload.admin_user.GetUserPage
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
        val queryResult = Mediator.queries.send(GetUserPage.Converter.INSTANCE.toQry(request))

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { GetUserPage.Converter.INSTANCE.fromApp(it) },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/changeStatus")
    fun changeStatus(@RequestBody @Validated request: ChangeStatus.Request) =
        Mediator.commands.send(
            ChangeStatus.Converter.INSTANCE.toCmd(request)
        )
}
