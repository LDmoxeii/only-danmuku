package edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2026/01/22
 */
@Aggregate(aggregate = "VideoHlsEncryptKey", name = "EncryptKeyStatus", type = "enum", description = "")
enum class EncryptKeyStatus(
    @field:JsonValue
    val code: Int,
    val desc: String
) {

    /**
     * 未知
     */
    UNKNOW(0, "未知"),

    /**
     * 可用
     */
    ACTIVE(1, "可用"),

    /**
     * 吊销
     */
    REVOKED(2, "吊销"),

    /**
     * 过期
     */
    EXPIRED(3, "过期"),
    ;

    companion object {
        private val enumMap: Map<Int, EncryptKeyStatus> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int?): EncryptKeyStatus {
            return valueOfOrNull(value) ?: throw KnownException("枚举类型 EncryptKeyStatus 枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): EncryptKeyStatus? {
            return enumMap[value]
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<EncryptKeyStatus, Int> {

        override fun convertToDatabaseColumn(attribute: EncryptKeyStatus): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): EncryptKeyStatus {
            return valueOf(dbData)
        }
    }
}
