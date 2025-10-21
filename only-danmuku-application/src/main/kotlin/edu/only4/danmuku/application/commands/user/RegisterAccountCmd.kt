package edu.only4.danmuku.application.commands.user

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.UniqueUserEmail
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.user.factory.UserFactory
import org.springframework.stereotype.Service

/**
 * 注册帐号
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object RegisterAccountCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.factories.create(
                UserFactory.Payload(
                    type = UserType.SYS_USER,
                    email = request.email,
                    nickName = request.nickName,
                    registerPassword = request.registerPassword
                )
            )

            Mediator.uow.save()
            return Response()
        }

    }

    class Request(
        @field:UniqueUserEmail
        val email: String,
        val nickName: String,
        val registerPassword: String,
    ) : RequestParam<Response>

    class Response
}
