package edu.only4.danmuku.domain.aggregates.video_file_post.enums
import com.fasterxml.jackson.annotation.JsonValue

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Aggregate(aggregate = "VideoFilePost", name = "UpdateType", type = "enum", description = "")
enum class UpdateType(
    @field:JsonValue
    val code: Int,
    val desc: String
) {

    /**
     * 未知类型
     */
    UNKNOW(0, "未知类型"),

    /**
     * 无更新
     */
    NO_UPDATE(1, "无更新"),

    /**
     * 有更新
     */
    HAS_UPDATE(2, "有更新"),
    ;

    companion object {
        private val enumMap: Map<Int, UpdateType> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int): UpdateType {
            return enumMap[value] ?: throw IllegalArgumentException("枚举类型UpdateType枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): UpdateType? {
            return if (value == null) null else valueOf(value)
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<UpdateType, Int> {

        override fun convertToDatabaseColumn(attribute: UpdateType): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): UpdateType {
            return valueOf(dbData)
        }
    }
}
