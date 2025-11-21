package edu.only4.danmuku.domain.aggregates.video_audit_trace.enums
import com.fasterxml.jackson.annotation.JsonValue

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Aggregate(aggregate = "VideoAuditTrace", name = "AuditStatus", type = "enum", description = "")
enum class AuditStatus(
    @field:JsonValue
    val code: Int,
    val desc: String
) {

    /**
     * 未知
     */
    UNKNOW(0, "未知"),

    /**
     * 审核通过
     */
    PASSED(1, "审核通过"),

    /**
     * 审核不通过
     */
    FAILED(2, "审核不通过"),
    ;

    companion object {
        private val enumMap: Map<Int, AuditStatus> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int): AuditStatus {
            return enumMap[value] ?: throw IllegalArgumentException("枚举类型AuditStatus枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): AuditStatus? {
            return if (value == null) null else valueOf(value)
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<AuditStatus, Int> {

        override fun convertToDatabaseColumn(attribute: AuditStatus): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): AuditStatus {
            return valueOf(dbData)
        }
    }
}
