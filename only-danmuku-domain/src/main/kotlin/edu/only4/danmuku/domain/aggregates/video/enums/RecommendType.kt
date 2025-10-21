package edu.only4.danmuku.domain.aggregates.video.enums

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Aggregate(aggregate = "Video", name = "RecommendType", type = "enum", description = "")
enum class RecommendType(
    val code: Int,
    val desc: String
) {

    /**
     * 未知状态
     */
    UNKNOW(0, "未知状态"),

    /**
     * 未推荐
     */
    NOT_RECOMMEND(1, "未推荐"),

    /**
     * 已推荐
     */
    RECOMMEND(2, "已推荐"),
    ;

    companion object {
        private val enumMap: Map<Int, RecommendType> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int): RecommendType {
            return enumMap[value] ?: throw IllegalArgumentException("枚举类型RecommendType枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): RecommendType? {
            return if (value == null) null else valueOf(value)
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<RecommendType, Int> {

        override fun convertToDatabaseColumn(attribute: RecommendType): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): RecommendType {
            return valueOf(dbData)
        }
    }
}
