package edu.only4.danmuku.application.commands.user_behavior

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginResult
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginType
import edu.only4.danmuku.domain.aggregates.user_login_log.factory.UserLoginLogFactory

import org.springframework.stereotype.Service

/**
 * 记录用户登录日志
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
object RecordLoginLogCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val occurTime = request.occurTime ?: (System.currentTimeMillis() / 1000L)
            val log = Mediator.factories.create(
                UserLoginLogFactory.Payload(
                    userId = request.userId,
                    userType = request.userType,
                    loginName = request.loginName,
                    loginType = request.loginType,
                    result = request.result,
                    ip = request.ip,
                    userAgent = request.userAgent,
                    reason = request.reason,
                    occurTime = occurTime
                )
            )

            Mediator.uow.save()

            return Response(
                logId = log.id
            )
        }

    }

    data class Request(
        val userId: Long?,
        val userType: UserType,
        val loginName: String,
        val loginType: LoginType,
        val result: LoginResult,
        val ip: String,
        val userAgent: String? = null,
        val reason: String? = null,
        val occurTime: Long? = null,
    ) : RequestParam<Response>

    data class Response(
        val logId: Long
    )
}
