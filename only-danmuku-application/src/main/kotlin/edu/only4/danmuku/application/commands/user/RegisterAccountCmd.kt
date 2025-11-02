package edu.only4.danmuku.application.commands.user

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.application.validator.UniqueUserEmail
import edu.only4.danmuku.application.validator.UniqueUserNickname
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
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            Mediator.factories.create(
                UserFactory.Payload(
                    type = UserType.SYS_USER,
                    email = request.email,
                    nickName = request.nickName,
                    registerPassword = request.registerPassword
                )
            )
            Mediator.uow.save()
        }
    }

    @UniqueUserEmail
    @UniqueUserNickname
    class Request(
        val email: String,
        val nickName: String,
        val registerPassword: String,
    ) : RequestParam<Unit>
}
