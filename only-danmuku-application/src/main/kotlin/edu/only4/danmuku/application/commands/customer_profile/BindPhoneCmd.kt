package edu.only4.danmuku.application.commands.customer_profile

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.customer_profile.SCustomerProfile
import org.springframework.stereotype.Service

/**
 * 绑定/变更用户手机号
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
object BindPhoneCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val profile = Mediator.repositories
                .findOne(SCustomerProfile.predicateById(request.customerProfileId))
                .orElseThrow { KnownException("用户档案不存在") }

            profile.bindPhone(request.phone)

            Mediator.uow.save()

            return Response()
        }

    }

    class Request(
        val customerProfileId: Long,
        val phone: String,
    ) : RequestParam<Response>

    class Response
}
