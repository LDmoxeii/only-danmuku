package edu.only4.danmuku.domain.aggregates.customer_message.extend

import com.only.engine.json.misc.JsonUtils
import jakarta.persistence.AttributeConverter

data class UserMessageExtend(
    val messageContent: String? = null,
    val messageContentReply: String? = null,
    val auditStatus: Int? = null,
) {

    class Converter : AttributeConverter<UserMessageExtend?, String?> {
        override fun convertToDatabaseColumn(attribute: UserMessageExtend?): String? =
            JsonUtils.toJsonString(attribute)

        override fun convertToEntityAttribute(dbData: String?): UserMessageExtend? =
            JsonUtils.parseObject(dbData, UserMessageExtend::class.java)
    }
}
