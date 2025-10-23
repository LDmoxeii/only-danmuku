package edu.only4.danmuku.application.validater

import edu.only4.danmuku.domain.aggregates.video_draft.enums.VideoStatus
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 校验审核状态是否为允许的取值
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidAuditStatus.Validator::class])
@MustBeDocumented
annotation class ValidAuditStatus(
    val message: String = "无效的审核状态",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {
    class Validator : ConstraintValidator<ValidAuditStatus, Int?> {
        private val allowedStatuses = setOf(
            VideoStatus.REVIEW_PASSED.code,
            VideoStatus.REVIEW_FAILED.code
        )

        override fun isValid(value: Int?, context: ConstraintValidatorContext): Boolean {
            if (value == null) {
                return true
            }
            return allowedStatuses.contains(value)
        }
    }
}
