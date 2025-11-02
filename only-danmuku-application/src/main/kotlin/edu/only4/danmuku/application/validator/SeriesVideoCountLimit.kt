package edu.only4.danmuku.application.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 校验：系列视频数量不得超过上限（Byte.MAX_VALUE）
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SeriesVideoCountLimit.Validator::class])
@MustBeDocumented
annotation class SeriesVideoCountLimit(
    val message: String = "系列视频数量超过允许上限",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val videoIdsField: String = "videoIds",
) {
    class Validator : ConstraintValidator<SeriesVideoCountLimit, Any> {
        private lateinit var videoIdsField: String

        override fun initialize(constraintAnnotation: SeriesVideoCountLimit) {
            videoIdsField = constraintAnnotation.videoIdsField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true
            val props = value::class.memberProperties.associateBy { it.name }
            val raw = props[videoIdsField]?.getter?.call(value) as? String? ?: return true
            val count = raw.split(',', '，')
                .mapNotNull { it.trim().trim('"').takeIf { s -> s.isNotEmpty() } }
                .toSet().size
            return count <= Byte.MAX_VALUE
        }
    }
}

