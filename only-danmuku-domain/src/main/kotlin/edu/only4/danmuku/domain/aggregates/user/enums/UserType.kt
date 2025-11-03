package edu.only4.danmuku.domain.aggregates.user.enums

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/11/03
 */
@Aggregate(aggregate = "User", name = "UserType", type = "enum", description = "")
enum class UserType(
    val code: Int,
    val desc: String
) {

    /**
     * 未知类型
     */
    UNKNOW(0, "未知类型"),

    /**
     * 系统管理员
     */
    SYS_USER(1, "系统管理员"),
    ;

    companion object {
        private val enumMap: Map<Int, UserType> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int): UserType {
            return enumMap[value] ?: throw IllegalArgumentException("枚举类型UserType枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): UserType? {
            return if (value == null) null else valueOf(value)
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<UserType, Int> {

        override fun convertToDatabaseColumn(attribute: UserType): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): UserType {
            return valueOf(dbData)
        }
    }
}
