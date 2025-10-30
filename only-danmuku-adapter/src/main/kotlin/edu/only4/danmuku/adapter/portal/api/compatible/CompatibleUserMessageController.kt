package edu.only4.danmuku.adapter.portal.api.compatible

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.*
import edu.only4.danmuku.application.queries.message.GetMessagePageQry
import edu.only4.danmuku.application.queries.message.GetNoReadMessageCountGroupQry
import edu.only4.danmuku.application.queries.message.GetNoReadMessageCountQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 用户消息控制器
 */
@RestController
@RequestMapping("/message")
@Validated
class CompatibleUserMessageController {

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
    fun getNoReadCountGroup(): List<MessageGetNoReadCountGroup.GroupItem> {
        val result = Mediator.queries.send(GetNoReadMessageCountGroupQry.Request())
        return result.list.map { item ->
            MessageGetNoReadCountGroup.GroupItem(
                messageType = item.messageType,
                count = item.count,
            )
        }
    }

    /**
     * 标记全部已读
     */
    @PostMapping("/readAll")
    fun messageReadAll(request: MessageReadAll.Request): MessageReadAll.Response {
        // TODO: 实现标记全部已读逻辑
        return MessageReadAll.Response()
    }

    /**
     * 加载消息列表
     */
    @PostMapping("/loadMessage")
    fun getMessagePage(@Validated request: MessageLoad.Request): PageData<MessageLoad.MessageItem> {
        val queryReq = GetMessagePageQry.Request(
            messageType = request.messageType,
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }
        val result = Mediator.queries.send(queryReq)
        val items = result.list.map {
            MessageLoad.MessageItem(
                id = it.id,
                messageType = it.messageType,
                readType = it.readType,
                extendJson = it.extendJson,
                createTime = it.createTime,
            )
        }
        return PageData.create(request, result.totalCount, items)
    }

    /**
     * 删除消息
     */
    @PostMapping("/delMessage")
    fun messageDel(@Validated request: MessageDel.Request): MessageDel.Response {
        // TODO: 实现删除消息逻辑
        return MessageDel.Response()
    }

}

