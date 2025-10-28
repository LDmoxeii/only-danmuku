package edu.only4.danmuku.adapter.portal.api.compatible

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.customer_action.*
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/userAction")
@Validated
class CompatibleUserActionController {

    @PostMapping("/doAction")
    fun doAction(
        videoId: Long,
        actionType: Int,
        @Max(2) @Min(1) actionCount: Int,
        commentId: Long?,
    ) {
        val userId = LoginHelper.getUserId()!!

        when (ActionType.valueOf(actionType)) {
            ActionType.LIKE_VIDEO -> Mediator.commands.send(
                LikeVideoCmd.Request(
                    videoId = videoId,
                    customerId = userId
                )
            )
            ActionType.FAVORITE_VIDEO -> Mediator.commands.send(
                CollectVideoCmd.Request(
                    videoId = videoId,
                    customerId = userId
                )
            )
            ActionType.COIN_VIDEO -> Mediator.commands.send(
                GiveVideoCoinCmd.Request(
                    videoId = videoId,
                    customerId = userId,
                    coinCount = actionCount
                )
            )
            ActionType.LIKE_COMMENT -> Mediator.commands.send(
                LikeCommentCmd.Request(
                    videoId = videoId,
                    commentId = commentId!!,
                    customerId = userId
                )
            )
            ActionType.HATE_COMMENT -> Mediator.commands.send(
                DislikeCommentCmd.Request(
                    videoId = videoId,
                    commentId = commentId!!,
                    customerId = userId
                )
            )
            else -> Unit
        }
    }

}
