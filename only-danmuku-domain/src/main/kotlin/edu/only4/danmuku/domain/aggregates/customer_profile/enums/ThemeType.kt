package edu.only4.danmuku.domain.aggregates.customer_profile.enums

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/11/03
 */
@Aggregate(aggregate = "CustomerProfile", name = "ThemeType", type = "enum", description = "")
enum class ThemeType(
    val code: Int,
    val desc: String
) {

    /**
     * 未知主题
     */
    UNKNOW(0, "未知主题"),

    /**
     * 浅色主题
     */
    LIGHT(1, "浅色主题"),

    /**
     * 深色主题
     */
    DARK(2, "深色主题"),

    /**
     * 跟随系统
     */
    SYSTEM(3, "跟随系统"),
    ;

    companion object {
        private val enumMap: Map<Int, ThemeType> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int): ThemeType {
            return enumMap[value] ?: throw IllegalArgumentException("枚举类型ThemeType枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): ThemeType? {
            return if (value == null) null else valueOf(value)
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<ThemeType, Int> {

        override fun convertToDatabaseColumn(attribute: ThemeType): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): ThemeType {
            return valueOf(dbData)
        }
    }
}
