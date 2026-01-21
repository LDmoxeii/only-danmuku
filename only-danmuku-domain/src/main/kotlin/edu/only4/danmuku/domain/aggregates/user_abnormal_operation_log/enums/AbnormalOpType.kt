package edu.only4.danmuku.domain.aggregates.user_abnormal_operation_log.enums
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
@Aggregate(aggregate = "UserAbnormalOperationLog", name = "AbnormalOpType", type = "enum", description = "")
enum class AbnormalOpType(
    @field:JsonValue
    val code: Int,
    val desc: String
) {

    /**
     * 未知异常
     */
    UNKNOW(0, "未知异常"),

    /**
     * 密码失败次数过多
     */
    PASSWORD_FAIL_TOO_MANY_TIMES(1, "密码失败次数过多"),
    ;

    companion object {
        private val enumMap: Map<Int, AbnormalOpType> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int?): AbnormalOpType {
            return valueOfOrNull(value) ?: throw KnownException("枚举类型 AbnormalOpType 枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): AbnormalOpType? {
            return enumMap[value]
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<AbnormalOpType, Int> {

        override fun convertToDatabaseColumn(attribute: AbnormalOpType): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): AbnormalOpType {
            return valueOf(dbData)
        }
    }
}
