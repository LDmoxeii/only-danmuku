package edu.only4.danmuku.application.commands.customer_action

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.VideoExists
import edu.only4.danmuku.domain._share.meta.customer_action.SCustomerAction
import edu.only4.danmuku.domain._share.meta.video.SVideo
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType
import edu.only4.danmuku.domain.aggregates.customer_action.factory.CustomerActionFactory
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 点赞视频（Toggle 逻辑）
 */
object LikeVideoCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val existing = Mediator.repositories.find(
                SCustomerAction.predicate { schema ->
                    schema.all(
                        schema.customerId eq request.customerId,
                        schema.videoId eq request.videoId,
                        schema.actionType eq ActionType.LIKE_VIDEO.code
                    )
                },
                persist = false
            )

            val isCancel: Boolean

            if (existing.isNotEmpty()) {
                // 已点赞 → 取消点赞并减少统计
                existing.forEach(Mediator.uow::remove)
                val video = Mediator.repositories.findOne(
                    SVideo.predicateById(request.videoId),
                    persist = false
                ).getOrNull() ?: throw KnownException("视频不存在")
                video.updateLikeCount(-1)
                isCancel = true
            } else {
                // 未点赞 → 创建点赞动作（统计增长由领域事件处理器负责）
                val video = Mediator.repositories.findOne(
                    SVideo.predicateById(request.videoId),
                    persist = false
                ).getOrNull() ?: throw KnownException("视频不存在")

                Mediator.factories.create(
                    CustomerActionFactory.Payload(
                        customerId = request.customerId.toString(),
                        videoId = request.videoId.toString(),
                        videoOwnerId = video.customerId.toString(),
                        commentId = 0L,
                        actionType = ActionType.LIKE_VIDEO,
                        actionCount = 1
                    )
                )
                isCancel = false
            }

            Mediator.uow.save()
            return Response(isCancel)
        }
    }

    data class Request(
        @field:VideoExists
        val videoId: Long,
        val customerId: Long
    ) : RequestParam<Response>

    data class Response(val isCancel: Boolean)
}

