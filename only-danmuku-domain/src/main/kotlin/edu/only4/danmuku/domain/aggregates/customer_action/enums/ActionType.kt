package edu.only4.danmuku.domain.aggregates.customer_action.enums

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/11/04
 */
@Aggregate(aggregate = "CustomerAction", name = "ActionType", type = "enum", description = "")
enum class ActionType(
    val code: Int,
    val desc: String
) {

    /**
     * 未知行为
     */
    UNKNOW(0, "未知行为"),

    /**
     * 评论喜欢点赞
     */
    LIKE_COMMENT(1, "评论喜欢点赞"),

    /**
     * 讨厌评论
     */
    HATE_COMMENT(2, "讨厌评论"),

    /**
     * 视频点赞
     */
    LIKE_VIDEO(3, "视频点赞"),

    /**
     * 视频收藏
     */
    FAVORITE_VIDEO(4, "视频收藏"),

    /**
     * 视频投币
     */
    COIN_VIDEO(5, "视频投币"),
    ;

    companion object {
        private val enumMap: Map<Int, ActionType> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int): ActionType {
            return enumMap[value] ?: throw IllegalArgumentException("枚举类型ActionType枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): ActionType? {
            return if (value == null) null else valueOf(value)
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<ActionType, Int> {

        override fun convertToDatabaseColumn(attribute: ActionType): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): ActionType {
            return valueOf(dbData)
        }
    }
}
