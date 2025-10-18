package edu.only4.danmuku.adapter.portal.api

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.UserActionDo
import edu.only4.danmuku.application.commands.customer_action.DoActionCmd
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 用户行为控制器
 */
@RestController
@RequestMapping("/userAction")
@Validated
class UserActionController {

    /**
     * 执行用户行为(点赞/收藏/投币)
     */
    @PostMapping("/doAction")
    fun userActionDo(@RequestBody @Validated request: UserActionDo.Request): UserActionDo.Response {
        // TODO: 从上下文获取当前用户ID
        val userId = 1L // 临时硬编码

        // 调用命令执行用户行为
        Mediator.commands.send(
            DoActionCmd.Request(
                userId = userId,
                videoId = request.videoId.toLong(),
                actionType = request.actionType,
                actionCount = request.actionCount ?: 1,
                commentId = request.commentId?.toLong()
            )
        )

        return UserActionDo.Response()
    }

}
