package edu.only4.danmuku.application.commands.user

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.user.SUser
import edu.only4.danmuku.domain.aggregates.user.User.Companion.isPasswordCorrect
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 更新登录信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object UpdateLoginInfoCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val user = Mediator.repositories.findOne(
                SUser.predicate{ it.email eq request.email }
            ).getOrNull() ?: throw KnownException("用户不存在")

            val isPasswordCorrect = isPasswordCorrect(user.password, request.password)
            require(isPasswordCorrect) { "密码错误" }

            user.updateLoginInfo(
                loginTime = System.currentTimeMillis() / 1000L,
                loginIp = request.loginIp
            )

            Mediator.uow.save()

            return Response(
                userId = user.id,
                userType = user.type,
                username = user.email
            )
        }

    }

    class Request(
        val email: String,
        val password: String,
        val loginIp: String,
    ) : RequestParam<Response>

    class Response(
        val userId: Long,
        val userType: UserType,
        val username: String,
    )
}
