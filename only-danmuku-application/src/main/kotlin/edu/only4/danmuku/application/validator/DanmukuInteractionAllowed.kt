package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.video.GetVideoInfoQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 校验视频弹幕功能是否开启
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DanmukuInteractionAllowed.Validator::class])
@MustBeDocumented
annotation class DanmukuInteractionAllowed(
    val message: String = "UP主已关闭弹幕",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {
    class Validator : ConstraintValidator<DanmukuInteractionAllowed, Long> {
        override fun isValid(value: Long?, context: ConstraintValidatorContext): Boolean {
            val videoId = value ?: return true
            val response = runCatching {
                Mediator.queries.send(GetVideoInfoQry.Request(videoId))
            }.getOrNull() ?: return true

            val interaction = response.interaction ?: return true
            return !interaction.contains("0")
        }
    }
}

