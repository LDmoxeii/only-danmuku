package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.user.CheckEmailExistsQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 验证邮箱唯一性的注解
 *
 * 默认读取 `email`、`userId` 字段，可在注解参数中自定义以实现更新场景的自我排除
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueUserEmail.Validator::class])
@MustBeDocumented
annotation class UniqueUserEmail(
    val message: String = "邮箱已被注册",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val emailField: String = "email",
    val userIdField: String = "userId",
) {

    /**
     * 邮箱唯一性验证器
     *
     * 验证用户注册时提供的邮箱是否已被其他用户使用
     */
    class Validator : ConstraintValidator<UniqueUserEmail, Any> {
        private lateinit var emailProperty: String
        private lateinit var userIdProperty: String

        override fun initialize(constraintAnnotation: UniqueUserEmail) {
            emailProperty = constraintAnnotation.emailField
            userIdProperty = constraintAnnotation.userIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) {
                return true
            }

            val props = value::class.memberProperties.associateBy { it.name }
            val email = props[emailProperty]?.getter?.call(value) as? String?
            val excludeUserId = userIdProperty
                .takeIf { it.isNotBlank() }
                ?.let { props[it]?.getter?.call(value) as? Long? }
                ?.takeIf { it != 0L }

            return isEmailUnique(email, excludeUserId)

        }

        private fun isEmailUnique(email: String?, excludeUserId: Long?): Boolean {
            val normalizedEmail = email?.trim().orEmpty()
            if (normalizedEmail.isBlank()) {
                return true
            }

            val queryResult = runCatching {
                Mediator.queries.send(
                    CheckEmailExistsQry.Request(
                        email = normalizedEmail,
                        excludeUserId = excludeUserId
                    )
                )
            }.getOrNull() ?: return false

            return !queryResult.exists
        }
    }
}
