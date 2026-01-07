package edu.only4.danmuku.adapter.portal.api.web

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.user_action.DoAction
import edu.only4.danmuku.application.commands.customer_action.*
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/userAction")
class UserActionController {

    @PostMapping("/doAction")
    fun doAction(@RequestBody @Validated request: DoAction.Request) {
        val userId = LoginHelper.getUserId()!!

        when (ActionType.valueOf(request.actionType)) {
            ActionType.LIKE_VIDEO -> Mediator.commands.send(
                LikeVideoCmd.Request(
                    videoId = request.videoId,
                    customerId = userId
                )
            )
            ActionType.FAVORITE_VIDEO -> Mediator.commands.send(
                CollectVideoCmd.Request(
                    videoId = request.videoId,
                    customerId = userId
                )
            )
            ActionType.COIN_VIDEO -> Mediator.commands.send(
                GiveVideoCoinCmd.Request(
                    videoId = request.videoId,
                    customerId = userId,
                    coinCount = request.actionCount
                )
            )
            ActionType.LIKE_COMMENT -> Mediator.commands.send(
                LikeCommentCmd.Request(
                    videoId = request.videoId,
                    commentId = request.commentId!!,
                    customerId = userId
                )
            )
            ActionType.HATE_COMMENT -> Mediator.commands.send(
                DislikeCommentCmd.Request(
                    videoId = request.videoId,
                    commentId = request.commentId!!,
                    customerId = userId
                )
            )
            else -> Unit
        }
    }

}
