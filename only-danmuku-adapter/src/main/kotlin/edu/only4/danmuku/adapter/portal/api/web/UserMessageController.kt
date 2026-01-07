package edu.only4.danmuku.adapter.portal.api.web

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.user_message.DeleteMessage
import edu.only4.danmuku.adapter.portal.api.payload.user_message.GetNoReadCountGroup
import edu.only4.danmuku.adapter.portal.api.payload.user_message.GetMessagePage
import edu.only4.danmuku.adapter.portal.api.payload.user_message.ReadAllMessage
import edu.only4.danmuku.application.commands.customer_message.DeleteMessageCmd
import edu.only4.danmuku.application.commands.customer_message.MarkAllAsReadCmd
import edu.only4.danmuku.application.queries.message.GetMessagePageQry
import edu.only4.danmuku.application.queries.message.GetNoReadMessageCountGroupQry
import edu.only4.danmuku.application.queries.message.GetNoReadMessageCountQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 用户消息控制器
 */
@RestController
@RequestMapping("/message")
class UserMessageController {

    /**
     * 获取未读消息数
     */
    @PostMapping("/getNoReadCount")
    fun getNoReadCount(): Long {
        val result = Mediator.queries.send(GetNoReadMessageCountQry.Request())
        return result.count
    }

    /**
     * 获取未读消息数(分组)
     */
    @PostMapping("/getNoReadCountGroup")
    fun getNoReadCountGroup(): List<GetNoReadCountGroup.Item> {
        val result = Mediator.queries.send(GetNoReadMessageCountGroupQry.Request())
        return result.list.map { GetNoReadCountGroup.Converter.INSTANCE.fromApp(it) }
    }

    /**
     * 标记全部已读
     */
    @PostMapping("/readAll")
    fun readAll(@RequestBody @Validated request: ReadAllMessage.Request) {
        val userId = LoginHelper.getUserId()!!
        Mediator.commands.send(
            MarkAllAsReadCmd.Request(
                customerId = userId,
                messageType = request.messageType
            )
        )
    }

    /**
     * 加载消息列表
     */
    @PostMapping("/page")
    fun page(@RequestBody @Validated request: GetMessagePage.Request): PageData<GetMessagePage.Item> {
        val queryReq = GetMessagePageQry.Request(
            messageType = request.messageType,
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }
        val result = Mediator.queries.send(queryReq)
        val items = result.list.map { GetMessagePage.Converter.INSTANCE.fromApp(it) }
        return PageData.create(request, result.totalCount, items)
    }

    @PostMapping("/delete")
    fun delete(@RequestBody @Validated request: DeleteMessage.Request) {
        val userId = LoginHelper.getUserId()!!
        Mediator.commands.send(
            DeleteMessageCmd.Request(
                customerId = userId,
                messageId = request.messageId
            )
        )
    }

}

