package edu.only4.danmuku.domain.aggregates.video_post_processing.enums
import com.fasterxml.jackson.annotation.JsonValue

import com.only.engine.exception.KnownException

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2026/01/14
 */
@Aggregate(aggregate = "VideoPostProcessing", name = "ProcessStatus", type = "enum", description = "")
enum class ProcessStatus(
    @field:JsonValue
    val code: Int,
    val desc: String
) {

    /**
     * 未知
     */
    UNKNOW(0, "未知"),

    /**
     * 待处理
     */
    PENDING(1, "待处理"),

    /**
     * 处理中
     */
    PROCESSING(2, "处理中"),

    /**
     * 成功
     */
    SUCCESS(3, "成功"),

    /**
     * 失败
     */
    FAILED(4, "失败"),

    /**
     * 跳过
     */
    SKIPPED(5, "跳过"),
    ;

    companion object {
        private val enumMap: Map<Int, ProcessStatus> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int?): ProcessStatus {
            return valueOfOrNull(value) ?: throw KnownException("枚举类型 ProcessStatus 枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): ProcessStatus? {
            return enumMap[value]
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<ProcessStatus, Int> {

        override fun convertToDatabaseColumn(attribute: ProcessStatus): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): ProcessStatus {
            return valueOf(dbData)
        }
    }
}
