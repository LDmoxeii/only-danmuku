package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.video.GetVideoInfoQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 验证视频是否存在
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [VideoExists.Validator::class])
@MustBeDocumented
annotation class VideoExists(
    val message: String = "视频不存在",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {
    class Validator : ConstraintValidator<VideoExists, Long> {
        override fun isValid(value: Long?, context: ConstraintValidatorContext): Boolean {
            val videoId = value ?: return true
            return runCatching {
                Mediator.queries.send(GetVideoInfoQry.Request(videoId))
            }.isSuccess
        }
    }
}

