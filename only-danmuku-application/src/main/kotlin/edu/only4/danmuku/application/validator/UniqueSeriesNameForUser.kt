package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.customer_video_series.CheckSeriesNameExistsQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 校验：同一用户下系列名称唯一（更新场景可排除自身）
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueSeriesNameForUser.Validator::class])
@MustBeDocumented
annotation class UniqueSeriesNameForUser(
    val message: String = "系列名称已存在",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val userIdField: String = "userId",
    val seriesIdField: String = "seriesId",
    val seriesNameField: String = "seriesName",
) {
    class Validator : ConstraintValidator<UniqueSeriesNameForUser, Any> {
        private lateinit var userIdField: String
        private lateinit var seriesIdField: String
        private lateinit var seriesNameField: String

        override fun initialize(constraintAnnotation: UniqueSeriesNameForUser) {
            userIdField = constraintAnnotation.userIdField
            seriesIdField = constraintAnnotation.seriesIdField
            seriesNameField = constraintAnnotation.seriesNameField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true
            val props = value::class.memberProperties.associateBy { it.name }
            val userId = (props[userIdField]?.getter?.call(value) as? Long) ?: return true
            val seriesId = (props[seriesIdField]?.getter?.call(value) as? Long?)
            val seriesName = (props[seriesNameField]?.getter?.call(value) as? String?)?.trim().orEmpty()
            if (seriesName.isBlank()) return true

            val exists = Mediator.queries.send(
                CheckSeriesNameExistsQry.Request(
                    customerId = userId,
                    seriesName = seriesName,
                    excludeSeriesId = seriesId
                )
            )

            return !exists.exists
        }
    }
}

