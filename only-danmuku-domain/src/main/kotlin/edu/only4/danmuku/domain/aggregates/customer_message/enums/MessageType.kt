package edu.only4.danmuku.domain.aggregates.customer_message.enums

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Aggregate(aggregate = "CustomerMessage", name = "MessageType", type = "enum", description = "")
enum class MessageType(
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
     * 评论回复
     */
    COMMENT_REPLY(2, "评论回复"),

    /**
     * 视频动态
     */
    VIDEO_DYNAMIC(3, "视频动态"),

    /**
     * 私信消息
     */
    PRIVATE_MESSAGE(4, "私信消息"),

    /**
     * 活动通知
     */
    ACTIVITY_NOTICE(5, "活动通知"),

    /**
     * 其他消息
     */
    OTHER_MESSAGE(6, "其他消息"),
    ;

    companion object {
        private val enumMap: Map<Int, MessageType> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int): MessageType {
            return enumMap[value] ?: throw IllegalArgumentException("枚举类型MessageType枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): MessageType? {
            return if (value == null) null else valueOf(value)
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
