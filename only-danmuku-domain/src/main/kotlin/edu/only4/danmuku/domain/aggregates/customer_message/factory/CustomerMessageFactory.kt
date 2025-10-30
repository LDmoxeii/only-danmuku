package edu.only4.danmuku.domain.aggregates.customer_message.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.customer_message.CustomerMessage
import edu.only4.danmuku.domain.aggregates.customer_message.enums.MessageType
import edu.only4.danmuku.domain.aggregates.customer_message.enums.ReadType

import org.springframework.stereotype.Service

/**
 * 用户消息表;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "CustomerMessage",
    name = "CustomerMessageFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class CustomerMessageFactory : AggregateFactory<CustomerMessageFactory.Payload, CustomerMessage> {

    override fun create(payload: Payload): CustomerMessage {
        return CustomerMessage(
            customerId = payload.customerId,
            videoId = payload.videoId,
            messageType = payload.messageType,
            sendSubjectId = payload.sendSubjectId,
            readType = payload.readType,
            extendJson = payload.extendJson,
            createUserId = null,
            createBy = null,
            createTime = payload.createTime,
            updateUserId = null,
            updateBy = null,
            updateTime = payload.updateTime ?: payload.createTime,
            deleted = 0L,
        )
    }

     @Aggregate(
        aggregate = "CustomerMessage",
        name = "CustomerMessagePayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val customerId: Long,
        val videoId: Long? = null,
        val messageType: MessageType,
        val sendSubjectId: Long? = null,
        val readType: ReadType = ReadType.UNREAD,
        val extendJson: String? = null,
        val createTime: Long,
        val updateTime: Long? = null,
    ) : AggregatePayload<CustomerMessage>

}
