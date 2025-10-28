package edu.only4.danmuku.adapter.portal.api.compatible

import edu.only4.danmuku.adapter.portal.api.payload.*
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
@Validated
class CompatibleUserMessageController {

    /**
     * 获取未读消息数
     */
    @PostMapping("/getNoReadCount")
    fun messageGetNoReadCount(): MessageGetNoReadCount.Response {
        // TODO: 实现获取未读消息数逻辑
        return MessageGetNoReadCount.Response()
    }

    /**
     * 获取未读消息数(分组)
     */
    @PostMapping("/getNoReadCountGroup")
    fun messageGetNoReadCountGroup(): MessageGetNoReadCountGroup.Response {
        // TODO: 实现获取未读消息数(分组)逻辑
        return MessageGetNoReadCountGroup.Response()
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
    fun messageLoad(@Validated request: MessageLoad.Request): MessageLoad.Response {
        // TODO: 实现加载消息列表逻辑
        return MessageLoad.Response()
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
