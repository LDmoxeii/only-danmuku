package edu.only4.danmuku.application.commands.customer_action

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.customer_profile.SCustomerProfile
import edu.only4.danmuku.domain._share.meta.video.SVideo
import org.springframework.stereotype.Service

/** 将“用户投币视频”事件落地到账户与视频统计 */
object ApplyCustomerCoinGivenCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val sender = Mediator.repositories.findFirst(
                SCustomerProfile.predicate { it.userId eq request.senderUserId }
            ).get()
            val receiver = Mediator.repositories.findFirst(
                SCustomerProfile.predicate { it.userId eq request.receiverUserId }
            ).get()
            sender.transferCoin(receiver, request.coinCount)

            val video = Mediator.repositories.findFirst(
                SVideo.predicateById(request.videoId)
            ).get()
            video.updateCoinCount(request.coinCount)

            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        val senderUserId: Long,
        val receiverUserId: Long,
        val videoId: Long,
        val coinCount: Int,
    ) : RequestParam<Response>

    class Response
}

