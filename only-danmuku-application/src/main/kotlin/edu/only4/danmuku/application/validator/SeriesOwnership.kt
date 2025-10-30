package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesInfoQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 校验视频系列归属权，确保操作者是系列所有者
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SeriesOwnership.Validator::class])
@MustBeDocumented
annotation class SeriesOwnership(
    val message: String = "无权操作该视频系列",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val seriesIdField: String = "seriesId",
    val operatorIdField: String = "operatorId",
) {
    class Validator : ConstraintValidator<SeriesOwnership, Any> {
        private lateinit var seriesIdField: String
        private lateinit var operatorIdField: String

        override fun initialize(constraintAnnotation: SeriesOwnership) {
            seriesIdField = constraintAnnotation.seriesIdField
            operatorIdField = constraintAnnotation.operatorIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }
            val seriesId = (props[seriesIdField]?.getter?.call(value) as? Long) ?: return true
            val operatorId = props[operatorIdField]?.getter?.call(value) as? Long? ?: return true

            val series = runCatching {
                Mediator.queries.send(GetCustomerVideoSeriesInfoQry.Request(seriesId))
            }.getOrNull() ?: return false

            return series.userId == operatorId
        }
    }
}
