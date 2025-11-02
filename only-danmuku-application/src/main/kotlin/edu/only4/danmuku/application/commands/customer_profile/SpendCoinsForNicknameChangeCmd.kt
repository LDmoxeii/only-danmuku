package edu.only4.danmuku.application.commands.customer_profile

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.application._share.config.properties.SysSettingProperties
import edu.only4.danmuku.domain._share.meta.customer_profile.SCustomerProfile
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 修改昵称扣减硬币
 */
object SpendCoinsForNicknameChangeCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val profile = Mediator.repositories.findFirst(
                SCustomerProfile.predicate { it.userId eq request.customerId },
            ).getOrNull() ?: throw KnownException("用户资料不存在：${request.customerId}")

            val sys = Mediator.ioc.getBean(SysSettingProperties::class.java)
            val amount = sys.renameNicknameCoinCost
            if (amount <= 0) return

            profile.spendCoins(amount)
            Mediator.uow.save()
        }
    }

    data class Request(
        val customerId: Long,
    ) : RequestParam<Unit>
}

