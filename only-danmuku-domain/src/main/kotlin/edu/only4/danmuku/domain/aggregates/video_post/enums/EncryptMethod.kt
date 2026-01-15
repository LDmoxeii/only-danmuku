package edu.only4.danmuku.domain.aggregates.video_post.enums

import com.fasterxml.jackson.annotation.JsonValue
import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/11/26
 */
@Aggregate(aggregate = "VideoPost", name = "EncryptMethod", type = "enum", description = "")
enum class EncryptMethod(
    @field:JsonValue
    val code: Int,
    val desc: String
) {

    /**
     * AES-128
     */
    HLS_AES_128(1, "AES-128"),

    /**
     * SAMPLE-AES
     */
    SAMPLE_AES(2, "SAMPLE-AES"),

    /**
     * DRM占位
     */
    DRM(3, "DRM占位"),
    ;

    companion object {
        private val enumMap: Map<Int, EncryptMethod> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int?): EncryptMethod {
            return valueOfOrNull(value)
                ?: throw KnownException("枚举类型 EncryptMethod 枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): EncryptMethod? {
            return enumMap[value]
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<EncryptMethod, Int> {

        override fun convertToDatabaseColumn(attribute: EncryptMethod): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): EncryptMethod {
            return valueOf(dbData)
        }
    }
}
