package edu.only4.danmuku.adapter.portal.api

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.UserActionDo
import edu.only4.danmuku.application.commands.customer_action.CollectVideoCmd
import edu.only4.danmuku.application.commands.customer_action.DislikeCommentCmd
import edu.only4.danmuku.application.commands.customer_action.GiveVideoCoinCmd
import edu.only4.danmuku.application.commands.customer_action.LikeCommentCmd
import edu.only4.danmuku.application.commands.customer_action.LikeVideoCmd
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType
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
     * 执行用户行为(点赞/收藏/投币/评论赞踩)
     */
    @PostMapping("/doAction")
    fun userActionDo(@RequestBody @Validated request: UserActionDo.Request): UserActionDo.Response {
        val userId = LoginHelper.getUserId()!!

        when (ActionType.valueOf(request.actionType)) {
            ActionType.LIKE_VIDEO -> Mediator.commands.send(
                LikeVideoCmd.Request(
                    videoId = request.videoId.toLong(),
                    customerId = userId
                )
            )
            ActionType.FAVORITE_VIDEO -> Mediator.commands.send(
                CollectVideoCmd.Request(
                    videoId = request.videoId.toLong(),
                    customerId = userId
                )
            )
            ActionType.COIN_VIDEO -> Mediator.commands.send(
                GiveVideoCoinCmd.Request(
                    videoId = request.videoId.toLong(),
                    customerId = userId,
                    coinCount = request.actionCount ?: 1
                )
            )
            ActionType.LIKE_COMMENT -> Mediator.commands.send(
                LikeCommentCmd.Request(
                    videoId = request.videoId.toLong(),
                    commentId = request.commentId!!.toLong(),
                    customerId = userId
                )
            )
            ActionType.HATE_COMMENT -> Mediator.commands.send(
                DislikeCommentCmd.Request(
                    videoId = request.videoId.toLong(),
                    commentId = request.commentId!!.toLong(),
                    customerId = userId
                )
            )
            else -> Unit
        }

        return UserActionDo.Response()
    }

}
