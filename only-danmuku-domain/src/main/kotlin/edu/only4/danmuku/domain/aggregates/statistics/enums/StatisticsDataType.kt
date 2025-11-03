package edu.only4.danmuku.domain.aggregates.statistics.enums

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/11/03
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
     * 播放量
     */
    PLAY(1, "播放量"),

    /**
     * 粉丝
     */
    FANS(2, "粉丝"),

    /**
     * 点赞
     */
    LIKE(3, "点赞"),

    /**
     * 收藏
     */
    COLLECTION(4, "收藏"),

    /**
     * 投币
     */
    COIN(5, "投币"),

    /**
     * 评论
     */
    COMMENT(6, "评论"),

    /**
     * 弹幕
     */
    DANMUKU(7, "弹幕"),
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
