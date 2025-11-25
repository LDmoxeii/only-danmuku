package edu.only4.danmuku.domain.aggregates.video_file_post.enums
import com.fasterxml.jackson.annotation.JsonValue

import com.only.engine.exception.KnownException

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Aggregate(aggregate = "VideoFilePost", name = "EncryptStatus", type = "enum", description = "")
enum class EncryptStatus(
    @field:JsonValue
    val code: Int,
    val desc: String
) {

    /**
     * 未加密
     */
    UNENCRYPTED(1, "未加密"),

    /**
     * 加密中
     */
    ENCRYPTING(2, "加密中"),

    /**
     * 已加密
     */
    ENCRYPTED(3, "已加密"),

    /**
     * 失败
     */
    FAILED(4, "失败"),
    ;

    companion object {
        private val enumMap: Map<Int, EncryptStatus> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int?): EncryptStatus {
            return valueOfOrNull(value) ?: throw KnownException("枚举类型 EncryptStatus 枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): EncryptStatus? {
            return enumMap[value]
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<EncryptStatus, Int> {

        override fun convertToDatabaseColumn(attribute: EncryptStatus): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): EncryptStatus {
            return valueOf(dbData)
        }
    }
}
