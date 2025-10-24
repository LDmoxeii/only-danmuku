package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.user.CheckUserExistsQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/** 校验目标用户是否存在（通过查询层） */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UserExists.Validator::class])
@MustBeDocumented
annotation class UserExists(
    val message: String = "目标用户不存在",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val targetIdField: String = "userId",
) {
    class Validator : ConstraintValidator<UserExists, Any> {
        private lateinit var targetIdField: String

        override fun initialize(annotation: UserExists) {
            targetIdField = annotation.targetIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true
            val props = value::class.memberProperties.associateBy { it.name }
            val targetId = (props[targetIdField]?.getter?.call(value) as? Long) ?: return true
            val resp = runCatching {
                Mediator.queries.send(CheckUserExistsQry.Request(userId = targetId))
            }.getOrNull() ?: return false
            return resp.exists
        }
    }
}
