package edu.only4.danmuku.application.commands.customer_action

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.NotDuplicateCoin
import edu.only4.danmuku.application.validater.NotSelfCoin
import edu.only4.danmuku.application.validater.SufficientCoinBalance
import edu.only4.danmuku.application.validater.VideoExists
import edu.only4.danmuku.domain._share.meta.video.SVideo
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType
import edu.only4.danmuku.domain.aggregates.customer_action.factory.CustomerActionFactory
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 视频投币（一次性操作，不可撤销）
 * - 验证：不能给自己投币
 * - 验证：同一视频只能投一次
 * - 验证：硬币余额充足
 */
object GiveVideoCoinCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 查询视频信息
            val video = Mediator.repositories.findOne(
                SVideo.predicateById(request.videoId),
                persist = false
            ).getOrNull() ?: throw KnownException("视频不存在")

            // 创建投币记录
            Mediator.factories.create(
                CustomerActionFactory.Payload(
                    customerId = request.customerId.toString(),
                    videoId = request.videoId.toString(),
                    videoOwnerId = video.customerId.toString(),
                    commentId = 0L,
                    actionType = ActionType.COIN_VIDEO,
                    actionCount = request.coinCount
                )
            )

            Mediator.uow.save()

            return Response(coinCount = request.coinCount)
        }
    }

    @NotSelfCoin(userIdField = "customerId", videoIdField = "videoId")
    @NotDuplicateCoin(userIdField = "customerId", videoIdField = "videoId")
    @SufficientCoinBalance(userIdField = "customerId", coinCountField = "coinCount")
    data class Request(
        @field:VideoExists
        val videoId: Long,
        val customerId: Long,
        @field:Min(1, message = "投币数量至少为1")
        @field:Max(2, message = "投币数量最多为2")
        val coinCount: Int
    ) : RequestParam<Response>

    data class Response(
        /** 投币数量 */
        val coinCount: Int
    )
}
