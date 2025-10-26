package edu.only4.danmuku.application.validater

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 安全文件路径校验注解
 *
 * 用于验证文件路径是否安全，防止路径遍历攻击
 * - 不允许包含 ".." 或 "./"
 * - 不允许包含反斜杠 "\"
 * - 不允许以 "/" 开头（绝对路径）
 * - 不允许包含 ":" （Windows盘符）
 */
@Constraint(validatedBy = [SafeFilePath.Validator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class SafeFilePath(
    val message: String = "非法的文件路径，存在安全风险",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {
    class Validator : ConstraintValidator<SafeFilePath, String> {
        override fun isValid(
            value: String?,
            context: ConstraintValidatorContext,
        ): Boolean {
            // 空值由 @NotEmpty 等注解处理
            if (value.isNullOrBlank()) {
                return true
            }

            // 检查路径遍历字符
            if (value.contains("..") || value.contains("./")) {
                return false
            }

            // 检查反斜杠（统一使用正斜杠）
            if (value.contains("\\")) {
                return false
            }

            // 检查是否为绝对路径
            if (value.startsWith("/")) {
                return false
            }

            // 检查Windows盘符
            if (value.contains(":")) {
                return false
            }

            return true
        }
    }
}
