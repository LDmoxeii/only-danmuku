package edu.only4.danmuku.domain.aggregates.user_abnormal_operation_log.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.user_abnormal_operation_log.UserAbnormalOperationLog
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.user_abnormal_operation_log.enums.AbnormalOpType

import org.springframework.stereotype.Service

/**
 * 用户异常操作日志;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
@Service
@Aggregate(
    aggregate = "UserAbnormalOperationLog",
    name = "UserAbnormalOperationLogFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class UserAbnormalOperationLogFactory : AggregateFactory<UserAbnormalOperationLogFactory.Payload, UserAbnormalOperationLog> {

    override fun create(payload: Payload): UserAbnormalOperationLog {
        return UserAbnormalOperationLog(
            userId = payload.userId,
            userType = payload.userType,
            opType = payload.opType,
            ip = payload.ip,
            occurTime = payload.occurTime,
            description = payload.description,
            extra = payload.extra
        )
    }

     @Aggregate(
        aggregate = "UserAbnormalOperationLog",
        name = "UserAbnormalOperationLogPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val userId: Long,
        val userType: UserType,
        val opType: AbnormalOpType,
        val ip: String,
        val occurTime: Long,
        val description: String?,
        val extra: String?,
    ) : AggregatePayload<UserAbnormalOperationLog>

}
