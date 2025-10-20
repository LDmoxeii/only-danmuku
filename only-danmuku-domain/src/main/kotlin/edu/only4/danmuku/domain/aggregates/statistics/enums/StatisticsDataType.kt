package edu.only4.danmuku.domain.aggregates.statistics.enums

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/10/20
 */
@Aggregate(aggregate = "Statistics", name = "StatisticsDataType", type = "enum", description = "")
enum class StatisticsDataType(
    val code: Int,
    val desc: String
) {

    /**
     * 未知类型
     */
    UNKNOW(0, "未知类型"),

    /**
     * 视频观看
     */
    VIDEO_VIEW(1, "视频观看"),

    /**
     * 视频点赞
     */
    VIDEO_LIKE(2, "视频点赞"),

    /**
     * 视频评论
     */
    VIDEO_COMMENT(3, "视频评论"),

    /**
     * 视频分享
     */
    VIDEO_SHARE(4, "视频分享"),

    /**
     * 用户关注
     */
    USER_FOLLOW(5, "用户关注"),

    /**
     * 用户登录
     */
    USER_LOGIN(6, "用户登录"),
    ;

    companion object {
        private val enumMap: Map<Int, StatisticsDataType> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int): StatisticsDataType {
            return enumMap[value] ?: throw IllegalArgumentException("枚举类型StatisticsDataType枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): StatisticsDataType? {
            return if (value == null) null else valueOf(value)
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<StatisticsDataType, Int> {

        override fun convertToDatabaseColumn(attribute: StatisticsDataType): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): StatisticsDataType {
            return valueOf(dbData)
        }
    }
}
