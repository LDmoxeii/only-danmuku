package edu.only4.danmuku.domain.aggregates.customer_message.enums
import com.fasterxml.jackson.annotation.JsonValue

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/11/04
 */
@Aggregate(aggregate = "CustomerMessage", name = "ReadType", type = "enum", description = "")
enum class ReadType(
    @field:JsonValue
    val code: Int,
    val desc: String
) {

    /**
     * 未知状态
     */
    UNKNOW(0, "未知状态"),

    /**
     * 未读
     */
    UNREAD(1, "未读"),

    /**
     * 已读
     */
    READ(2, "已读"),
    ;

    companion object {
        private val enumMap: Map<Int, ReadType> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int): ReadType {
            return enumMap[value] ?: throw IllegalArgumentException("枚举类型ReadType枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): ReadType? {
            return if (value == null) null else valueOf(value)
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<ReadType, Int> {

        override fun convertToDatabaseColumn(attribute: ReadType): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): ReadType {
            return valueOf(dbData)
        }
    }
}
