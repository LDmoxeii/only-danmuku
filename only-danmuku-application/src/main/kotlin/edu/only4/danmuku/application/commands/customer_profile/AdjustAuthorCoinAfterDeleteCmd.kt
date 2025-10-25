package edu.only4.danmuku.application.commands.customer_profile

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application._share.enums.config.properties.SysSettingProperties
import edu.only4.danmuku.domain._share.meta.customer_profile.SCustomerProfile
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 视频删除后回收对应用户硬币数
 */
object AdjustAuthorCoinAfterDeleteCmd {

    @Service
    class Handler(
        private val sysSettingProperties: SysSettingProperties,
    ) : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val deduction = sysSettingProperties.postVideoCoinCount
            if (deduction <= 0) {
                return Response(deducted = false, coinAmount = 0)
            }

            val profile = Mediator.repositories.findFirst(
                SCustomerProfile.predicate { it.userId eq request.authorId },
                persist = true
            ).getOrNull() ?: return Response(deducted = false, coinAmount = 0)

            profile.reclaimRewardCoins(deduction)
            Mediator.uow.save()

            return Response(deducted = true, coinAmount = deduction)
        }
    }

    data class Request(
        val authorId: Long,
        val videoId: Long,
    ) : RequestParam<Response>

    data class Response(
        val deducted: Boolean,
        val coinAmount: Int,
    )
}

