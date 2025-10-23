package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.domain._share.meta.video_draft.SVideoDraft
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 校验视频草稿是否存在
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [VideoDraftExists.Validator::class])
@MustBeDocumented
annotation class VideoDraftExists(
    val message: String = "视频草稿不存在",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val videoIdField: String = "videoId",
) {

    class Validator : ConstraintValidator<VideoDraftExists, Any> {
        private lateinit var videoIdProperty: String

        override fun initialize(constraintAnnotation: VideoDraftExists) {
            videoIdProperty = constraintAnnotation.videoIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            val videoId = extractVideoId(value) ?: return true

            val draft = runCatching {
                Mediator.repositories.findFirst(
                    SVideoDraft.predicate { it.videoId eq videoId },
                    persist = false
                ).getOrNull()
            }.getOrNull()

            return draft != null
        }

        private fun extractVideoId(source: Any?): Long? {
            if (source == null) {
                return null
            }

            return when (source) {
                is Number -> source.toLong()
                is String -> source.toLongOrNull()
                else -> {
                    val props = source::class.memberProperties.associateBy { it.name }
                    val rawValue = props[videoIdProperty]?.getter?.call(source) ?: return null
                    when (rawValue) {
                        is Number -> rawValue.toLong()
                        is String -> rawValue.toLongOrNull()
                        else -> null
                    }
                }
            }?.takeIf { it > 0 }
        }
    }
}
