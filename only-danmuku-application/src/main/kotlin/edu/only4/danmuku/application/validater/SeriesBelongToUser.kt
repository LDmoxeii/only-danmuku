package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.customer_video_series.CheckSeriesExistsQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 验证所有系列属于当前用户
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SeriesBelongToUser.Validator::class])
@MustBeDocumented
annotation class SeriesBelongToUser(
    val message: String = "存在不属于当前用户的系列",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val userIdField: String = "userId",
    val seriesIdsField: String = "seriesIds"
) {
    class Validator : ConstraintValidator<SeriesBelongToUser, Any> {
        private lateinit var userIdField: String
        private lateinit var seriesIdsField: String

        override fun initialize(constraintAnnotation: SeriesBelongToUser) {
            this.userIdField = constraintAnnotation.userIdField
            this.seriesIdsField = constraintAnnotation.seriesIdsField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }
            val userId = (props[userIdField]?.getter?.call(value) as? Long) ?: return true
            val seriesIds = props[seriesIdsField]?.getter?.call(value) as? List<*> ?: return true

            if (seriesIds.isEmpty()) return true

            // 验证每个 seriesId 是否属于当前用户
            for (seriesId in seriesIds) {
                if (seriesId !is Long) continue

                val result = Mediator.queries.send(
                    CheckSeriesExistsQry.Request(
                        seriesId = seriesId,
                        userId = userId
                    )
                )

                // 如果系列不存在或不属于当前用户，验证失败
                if (!result.exists) {
                    return false
                }
            }

            return true
        }
    }
}
