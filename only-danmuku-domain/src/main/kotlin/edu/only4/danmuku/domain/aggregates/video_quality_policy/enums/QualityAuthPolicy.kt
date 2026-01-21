package edu.only4.danmuku.domain.aggregates.video_quality_policy.enums
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
@Aggregate(aggregate = "VideoQualityPolicy", name = "QualityAuthPolicy", type = "enum", description = "")
enum class QualityAuthPolicy(
    @field:JsonValue
    val code: Int,
    val desc: String
) {

    /**
     * 未知
     */
    UNKNOW(0, "未知"),

    /**
     * 公开
     */
    PUBLIC(1, "公开"),

    /**
     * 登录
     */
    LOGIN(2, "登录"),

    /**
     * 付费
     */
    PAID(3, "付费"),

    /**
     * 自定义
     */
    CUSTOM(4, "自定义"),
    ;

    companion object {
        private val enumMap: Map<Int, QualityAuthPolicy> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int?): QualityAuthPolicy {
            return valueOfOrNull(value) ?: throw KnownException("枚举类型 QualityAuthPolicy 枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): QualityAuthPolicy? {
            return enumMap[value]
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<QualityAuthPolicy, Int> {

        override fun convertToDatabaseColumn(attribute: QualityAuthPolicy): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): QualityAuthPolicy {
            return valueOf(dbData)
        }
    }
}
