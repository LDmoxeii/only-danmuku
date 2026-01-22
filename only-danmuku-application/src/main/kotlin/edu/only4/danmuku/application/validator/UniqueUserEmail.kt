package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.user.UniqueUserEmailQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueUserEmail.Validator::class])
@MustBeDocumented
annotation class UniqueUserEmail(
    val message: String = "唯一性校验未通过",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val emailField: String = "email",
    val userIdField: String = "userId",
) {
    class Validator : ConstraintValidator<UniqueUserEmail, Any> {
        private lateinit var emailProperty: String
        private lateinit var userIdProperty: String

        override fun initialize(constraintAnnotation: UniqueUserEmail) {
            emailProperty = constraintAnnotation.emailField
            userIdProperty = constraintAnnotation.userIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }

            // 读取唯一字段值
            val email = props[emailProperty]?.getter?.call(value) as? String?
            val emailTrimmed = email?.trim()

            // 读取排除 ID
            val excludeId = props[userIdProperty]?.getter?.call(value) as? Long

            // 所有参数均有值（字符串非空）才进行校验
            val allPresent =
                (emailTrimmed != null && emailTrimmed.isNotBlank())
            if (!allPresent) return true

            val result = runCatching {
                Mediator.queries.send(
                    UniqueUserEmailQry.Request(
                        email = emailTrimmed!!,
                        excludeUserId = excludeId,
                    )
                )
            }.getOrNull() ?: return false

            return !result.exists
        }
    }
}
