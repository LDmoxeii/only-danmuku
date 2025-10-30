package edu.only4.danmuku.application.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/** 通用“不能对自己操作”的校验器 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NotSelf.Validator::class])
@MustBeDocumented
annotation class NotSelf(
    val message: String = "不能对自己进行该操作",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val userIdField: String = "userId",
    val targetIdField: String = "targetId",
) {
    class Validator : ConstraintValidator<NotSelf, Any> {
        private lateinit var userIdField: String
        private lateinit var targetIdField: String

        override fun initialize(annotation: NotSelf) {
            userIdField = annotation.userIdField
            targetIdField = annotation.targetIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true
            val props = value::class.memberProperties.associateBy { it.name }
            val userId = (props[userIdField]?.getter?.call(value) as? Long) ?: return true
            val targetId = (props[targetIdField]?.getter?.call(value) as? Long) ?: return true
            return userId != targetId
        }
    }
}

