package edu.only4.danmuku.adapter.portal.api

import edu.only4.danmuku.adapter.portal.api.payload.*
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * 用户消息控制器
 */
@RestController
@RequestMapping("/message/v2")
@Validated
class MessageController {

    /**
     * 获取未读消息数
     */
    @GetMapping("/getNoReadCount")
    fun messageGetNoReadCount(): MessageGetNoReadCount.Response {
        // TODO: 实现获取未读消息数逻辑
        return MessageGetNoReadCount.Response()
    }

    /**
     * 获取未读消息数(分组)
     */
    @GetMapping("/getNoReadCountGroup")
    fun messageGetNoReadCountGroup(): MessageGetNoReadCountGroup.Response {
        // TODO: 实现获取未读消息数(分组)逻辑
        return MessageGetNoReadCountGroup.Response()
    }

    /**
     * 标记全部已读
     */
    @PostMapping("/readAll")
    fun messageReadAll(@RequestBody request: MessageReadAll.Request): MessageReadAll.Response {
        // TODO: 实现标记全部已读逻辑
        return MessageReadAll.Response()
    }

    /**
     * 加载消息列表
     */
    @PostMapping("/loadMessage")
    fun messageLoad(@RequestBody @Validated request: MessageLoad.Request): MessageLoad.Response {
        // TODO: 实现加载消息列表逻辑
        return MessageLoad.Response()
    }

    /**
     * 删除消息
     */
    @PostMapping("/delMessage")
    fun messageDel(@RequestBody @Validated request: MessageDel.Request): MessageDel.Response {
        // TODO: 实现删除消息逻辑
        return MessageDel.Response()
    }

}
