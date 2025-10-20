package edu.only4.danmuku.domain.aggregates.video_file_draft.enums

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/20
 */
@Aggregate(aggregate = "VideoFileDraft", name = "TransferResult", type = "enum", description = "")
enum class TransferResult(
    val code: Int,
    val desc: String
) {

    /**
     * 未知结果
     */
    UNKNOW(0, "未知结果"),

    /**
     * 转码中
     */
    TRANSCODING(1, "转码中"),

    /**
     * 转码成功
     */
    SUCCESS(2, "转码成功"),

    /**
     * 转码失败
     */
    FAILED(3, "转码失败"),
    ;

    companion object {
        private val enumMap: Map<Int, TransferResult> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int): TransferResult {
            return enumMap[value] ?: throw IllegalArgumentException("枚举类型TransferResult枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): TransferResult? {
            return if (value == null) null else valueOf(value)
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<TransferResult, Int> {

        override fun convertToDatabaseColumn(attribute: TransferResult): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): TransferResult {
            return valueOf(dbData)
        }
    }
}
