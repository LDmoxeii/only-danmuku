package edu.only4.danmuku.application.commands.customer_profile

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain.aggregates.customer_profile.factory.CustomerProfileFactory
import org.springframework.stereotype.Service

/**
 * 创建用户档案
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object CreateCustomerProfileCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.factories.create(
                CustomerProfileFactory.Payload(
                    userid = request.userid,
                    nickName = request.nickName,
                    email = request.email,
                    registerCoinCount = request.registerCoinCount,
                )
            )

            Mediator.uow.save()

            return Response()
        }

    }

    class Request(
        val userid: Long,
        val nickName: String,
        val email: String,
        val registerCoinCount: Int,
    ) : RequestParam<Response>

    class Response(
    )
}
