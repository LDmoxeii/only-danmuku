package edu.only4.danmuku.application.commands.user

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.user.SUser
import jakarta.validation.constraints.NotNull
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 切换账号状态命令
 */
object ChangeAccountStatusCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val user = Mediator.repositories.findFirst(
                SUser.predicateById(request.userId)
            ).getOrNull() ?: throw KnownException("用户不存在：${request.userId}")

            user.changeStatus(request.status)

            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        /** 用户ID */
        @field:NotNull(message = "用户ID不能为空")
        val userId: Long,

        /** 状态：true-启用，false-禁用 */
        @field:NotNull(message = "状态不能为空")
        val status: Boolean,
    ) : RequestParam<Response>

    class Response
}
