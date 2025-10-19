package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.user.CheckEmailExistsQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 验证邮箱唯一性的注解
 *
 * 用于验证用户注册时邮箱是否已被使用
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueUserEmail.Validator::class])
@MustBeDocumented
annotation class UniqueUserEmail(
    val message: String = "邮箱已被注册",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {

    /**
     * 邮箱唯一性验证器
     *
     * 验证用户注册时提供的邮箱是否已被其他用户使用
     */
    class Validator : ConstraintValidator<UniqueUserEmail, String> {

        override fun isValid(email: String?, context: ConstraintValidatorContext): Boolean {
            // 空值由 @NotBlank 等其他注解处理
            if (email.isNullOrBlank()) {
                return true
            }

            // 通过 CQRS 查询检查邮箱是否已存在
            return !Mediator.queries.send(
                CheckEmailExistsQry.Request(email = email)
            ).exists
        }
    }
}
