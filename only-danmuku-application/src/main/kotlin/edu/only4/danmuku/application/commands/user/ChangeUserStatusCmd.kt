package edu.only4.danmuku.application.commands.user

import com.only.engine.error.CommonErrors
import com.only.engine.exception.AppException
import com.only.engine.exception.BusinessException
import com.only.engine.exception.DependencyException
import com.only.engine.exception.RequestException
import com.only.engine.exception.SystemException
import edu.only4.danmuku.domain.shared.error.DanmukuBusinessErrors
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
            ).getOrNull() ?: throw BusinessException(DanmukuBusinessErrors.RESOURCE_NOT_FOUND, "用户不存在：${request.userId}")

            user.changeStatus(request.status)

            Mediator.uow.save()
        }
    }

    data class Request(
        val userId: Long,
        val status: Boolean,
    ) : RequestParam<Unit>
}
