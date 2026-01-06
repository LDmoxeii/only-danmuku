package edu.only4.danmuku.domain.aggregates.customer_message.enums
import com.fasterxml.jackson.annotation.JsonValue

import com.only.engine.exception.KnownException

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
@Aggregate(aggregate = "CustomerMessage", name = "MessageType", type = "enum", description = "")
enum class MessageType(
    @field:JsonValue
    val code: Int,
    val desc: String
) {

    /**
     * 未知消息
     */
    UNKNOW(0, "未知消息"),

    /**
     * 系统消息
     */
    SYSTEM_MESSAGE(1, "系统消息"),

    /**
     * 收到的赞
     */
    LIKE_MESSAGE(2, "收到的赞"),

    /**
     * 收到收藏
     */
    COLLECTION_MESSAGE(3, "收到收藏"),

    /**
     * 评论和@
     */
    COMMENT_MENTION(4, "评论和@"),

    /**
     * 私信消息
     */
    PRIVATE_MESSAGE(5, "私信消息"),
    ;

    companion object {
        private val enumMap: Map<Int, MessageType> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int?): MessageType {
            return valueOfOrNull(value) ?: throw KnownException("枚举类型 MessageType 枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): MessageType? {
            return enumMap[value]
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<MessageType, Int> {

        override fun convertToDatabaseColumn(attribute: MessageType): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): MessageType {
            return valueOf(dbData)
        }
    }
}
