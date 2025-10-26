package edu.only4.danmuku.application.commands.customer_profile

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application._share.config.properties.SysSettingProperties
import edu.only4.danmuku.domain._share.meta.customer_profile.SCustomerProfile
import edu.only4.danmuku.domain._share.meta.video.SVideo
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 首次发布视频奖励硬币
 */
object RewardUserForVideoCmd {

    @Service
    class Handler(
        private val sysSettingProperties: SysSettingProperties,
    ) : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val existingVideo = Mediator.repositories.findFirst(
                SVideo.predicateById(request.videoId),
                persist = false
            ).getOrNull()
            if (existingVideo != null) {
                return Response(rewarded = false, coinAmount = 0)
            }

            val profile = Mediator.repositories.findFirst(
                SCustomerProfile.predicate { it.userId eq request.customerId },
                persist = true
            ).getOrNull() ?: throw KnownException("用户资料不存在: ")

            val rewardAmount = sysSettingProperties.postVideoCoinCount
            profile.rewardCoins(rewardAmount)

            Mediator.uow.save()
            return Response(rewarded = true, coinAmount = rewardAmount)
        }
    }

    data class Request(
        val customerId: Long,
        val videoId: Long,
    ) : RequestParam<Response>

    data class Response(
        val rewarded: Boolean,
        val coinAmount: Int,
    )
}
