package edu.only4.danmuku.domain.aggregates.video.enums
import com.fasterxml.jackson.annotation.JsonValue

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/11/04
 */
@Aggregate(aggregate = "Video", name = "PostType", type = "enum", description = "")
enum class PostType(
    @get:JsonValue
    val code: Int,
    val desc: String
) {

    /**
     * 未知类型
     */
    UNKNOW(0, "未知类型"),

    /**
     * 自制作
     */
    ORIGINAL(1, "自制作"),

    /**
     * 转载
     */
    REPOST(2, "转载"),
    ;

    companion object {
        private val enumMap: Map<Int, PostType> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int): PostType {
            return enumMap[value] ?: throw IllegalArgumentException("枚举类型PostType枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): PostType? {
            return if (value == null) null else valueOf(value)
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<PostType, Int> {

        override fun convertToDatabaseColumn(attribute: PostType): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): PostType {
            return valueOf(dbData)
        }
    }
}
