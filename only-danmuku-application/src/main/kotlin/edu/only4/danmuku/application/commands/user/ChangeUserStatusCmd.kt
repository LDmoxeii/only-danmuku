package edu.only4.danmuku.application.commands.user

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.domain._share.meta.user.SUser
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 切换账号状态命令
 */
object ChangeUserStatusCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val user = Mediator.repositories.findFirst(
                SUser.predicateById(request.userId)
            ).getOrNull() ?: throw KnownException("用户不存在：${request.userId}")

            user.changeStatus(request.status)

            Mediator.uow.save()
        }
    }

    data class Request(
        val userId: Long,
        val status: Boolean,
    ) : RequestParam<Unit>
}
