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
 * 收藏视频（Toggle 逻辑）
 * - 已收藏 → 取消收藏
 * - 未收藏 → 新增收藏
 */
object CollectVideoCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 查询已有收藏记录
            val existingAction = Mediator.repositories.find(
                SCustomerAction.predicate { schema ->
                    schema.all(
                        schema.customerId eq request.customerId,
                        schema.videoId eq request.videoId,
                        schema.actionType eq ActionType.FAVORITE_VIDEO.code
                    )
                },
                persist = false
            )

            val isCancel: Boolean

            if (existingAction.isNotEmpty()) {
                // 已收藏 → 删除记录（取消收藏），并减少视频收藏统计
                existingAction.forEach(Mediator.uow::remove)

                // TODO 发出删除收藏事件 -> 减少视频收藏统计命令
                val video = Mediator.repositories.findOne(
                    SVideo.predicateById(request.videoId),
                    persist = false
                ).getOrNull() ?: throw KnownException("视频不存在")
                video.updateCollectCount(-1)

                isCancel = true
            } else {
                // 查询视频信息
                val video = Mediator.repositories.findOne(
                    SVideo.predicateById(request.videoId),
                    persist = false
                ).getOrNull() ?: throw KnownException("视频不存在")

                // 未收藏 → 创建记录（新增收藏）
                Mediator.factories.create(
                    CustomerActionFactory.Payload(
                        customerId = request.customerId.toString(),
                        videoId = request.videoId.toString(),
                        videoOwnerId = video.customerId.toString(),
                        commentId = 0L,
                        actionType = ActionType.FAVORITE_VIDEO,
                        actionCount = 1
                    )
                )

                isCancel = false
            }

            Mediator.uow.save()

            return Response(isCancel = isCancel)
        }
    }

    data class Request(
        @field:VideoExists
        val videoId: Long,
        val customerId: Long
    ) : RequestParam<Response>

    data class Response(
        /** 是否为取消操作 */
        val isCancel: Boolean
    )
}
