package edu.only4.danmuku.application.commands.user

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.user.SUser

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 禁用帐号
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 */
object DisableAccountCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val user = Mediator.repositories.findFirst(
                SUser.predicateById(request.userId)
            ).getOrNull() ?: throw KnownException("用户不存在：${request.userId}")

            user.disableAccount()

            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        /** 用户ID */
        val userId: Long
    ) : RequestParam<Response>

    class Response
}
