package edu.only4.danmuku.domain.aggregates.customer_profile.enums
import com.fasterxml.jackson.annotation.JsonValue

import com.only.engine.exception.KnownException

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2026/01/21
 */
@Aggregate(aggregate = "CustomerProfile", name = "SexType", type = "enum", description = "")
enum class SexType(
    @field:JsonValue
    val code: Int,
    val desc: String
) {

    /**
     * 未知
     */
    UNKNOWN(0, "未知"),

    /**
     * 女
     */
    FEMALE(1, "女"),

    /**
     * 男
     */
    MALE(2, "男"),
    ;

    companion object {
        private val enumMap: Map<Int, SexType> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int?): SexType {
            return valueOfOrNull(value) ?: throw KnownException("枚举类型 SexType 枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): SexType? {
            return enumMap[value]
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<SexType, Int> {

        override fun convertToDatabaseColumn(attribute: SexType): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): SexType {
            return valueOf(dbData)
        }
    }
}
