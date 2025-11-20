package edu.only4.danmuku.domain.aggregates.user_login_log.enums
import com.fasterxml.jackson.annotation.JsonValue

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.AttributeConverter

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件，重新生成会覆盖该文件
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
@Aggregate(aggregate = "UserLoginLog", name = "LoginResult", type = "enum", description = "")
enum class LoginResult(
    @field:JsonValue
    val code: Int,
    val desc: String
) {

    /**
     * 未知结果
     */
    UNKNOW(0, "未知结果"),

    /**
     * 成功
     */
    SUCCESS(1, "成功"),

    /**
     * 失败
     */
    FAILURE(2, "失败"),
    ;

    companion object {
        private val enumMap: Map<Int, LoginResult> by lazy {
            entries.associateBy { it.code }
        }

        fun valueOf(value: Int): LoginResult {
            return enumMap[value] ?: throw IllegalArgumentException("枚举类型LoginResult枚举值转换异常，不存在的值: $value")
        }

        fun valueOfOrNull(value: Int?): LoginResult? {
            return if (value == null) null else valueOf(value)
        }
    }

    /**
     * JPA转换器
     */
    class Converter : AttributeConverter<LoginResult, Int> {

        override fun convertToDatabaseColumn(attribute: LoginResult): Int {
            return attribute.code
        }

        override fun convertToEntityAttribute(dbData: Int): LoginResult {
            return valueOf(dbData)
        }
    }
}
