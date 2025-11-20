package edu.only4.danmuku.application.commands.user_behavior

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.user_abnormal_operation_log.enums.AbnormalOpType
import edu.only4.danmuku.domain.aggregates.user_abnormal_operation_log.factory.UserAbnormalOperationLogFactory

import org.springframework.stereotype.Service

/**
 * 记录用户异常操作日志
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
object RecordAbnormalOperationCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val occurTime = request.occurTime ?: System.currentTimeMillis() / 1000L
            val abnormalLog = Mediator.factories.create(
                UserAbnormalOperationLogFactory.Payload(
                    userId = request.userId,
                    userType = request.userType,
                    opType = request.opType,
                    ip = request.ip,
                    occurTime = occurTime,
                    description = request.description,
                    extra = request.extra
                )
            )

            Mediator.uow.save()

            return Response(
                abnormalOperationLogId = abnormalLog.id
            )
        }

    }

    data class Request(
        val userId: Long,
        val userType: UserType,
        val opType: AbnormalOpType,
        val ip: String,
        val occurTime: Long? = null,
        val description: String? = null,
        val extra: String? = null,
    ) : RequestParam<Response>

    data class Response(
        val abnormalOperationLogId: Long
    )
}
