package edu.only4.danmuku.domain.aggregates.video_hls_key_token.enums
import com.fasterxml.jackson.annotation.JsonValue

import com.only.engine.exception.KnownException

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
@Aggregate(aggregate = "VideoHlsKeyToken", name = "EncryptTokenStatus", type = "enum", description = "")
enum class EncryptTokenStatus(
    @field:JsonValue
    val code: Int,
    val desc: String
) {

    /**
     * 未知
     */
    UNKNOW(0, "未知"),

    /**
     * 有效
     */
    VALID(1, "有效"),

    /**
     * 已用尽
     */
    EXHAUSTED(2, "已用尽"),

    /**
     * 过期
     */
    EXPIRED(3, "过期"),

    /**
     * 吊销
     */
    REVOKED(4, "吊销"),
    ;

    companion object {
        private val enumMap: Map<Int, EncryptTokenStatus> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int?): EncryptTokenStatus {
            return valueOfOrNull(value) ?: throw KnownException("枚举类型 EncryptTokenStatus 枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): EncryptTokenStatus? {
            return enumMap[value]
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<EncryptTokenStatus, Int> {

        override fun convertToDatabaseColumn(attribute: EncryptTokenStatus): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): EncryptTokenStatus {
            return valueOf(dbData)
        }
    }
}
