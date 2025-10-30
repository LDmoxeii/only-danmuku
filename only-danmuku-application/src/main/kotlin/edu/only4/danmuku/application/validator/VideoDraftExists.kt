package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.video_draft.GetVideoDraftInfoQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/** 校验视频草稿是否存在（通过查询层） */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [VideoDraftExists.Validator::class])
@MustBeDocumented
annotation class VideoDraftExists(
    val message: String = "视频草稿不存在",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val videoIdField: String = "videoId",
    val userIdField: String = "customerId",
) {

    class Validator : ConstraintValidator<VideoDraftExists, Any> {
        private lateinit var videoIdProperty: String
        private lateinit var userIdProperty: String

        override fun initialize(constraintAnnotation: VideoDraftExists) {
            videoIdProperty = constraintAnnotation.videoIdField
            userIdProperty = constraintAnnotation.userIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            val (videoId, userId) = extractIds(value) ?: return true

            val resp = runCatching {
                Mediator.queries.send(
                    GetVideoDraftInfoQry.Request(
                        videoId = videoId,
                        userId = userId
                    )
                )
            }.getOrNull()

            return resp != null
        }

        private fun extractIds(source: Any?): Pair<Long, Long>? {
            if (source == null) return null
            return when (source) {
                is Number, is String -> null
                else -> {
                    val props = source::class.memberProperties.associateBy { it.name }
                    val vRaw = props[videoIdProperty]?.getter?.call(source)
                    val uRaw = props[userIdProperty]?.getter?.call(source)
                    val vId = when (vRaw) {
                        is Number -> vRaw.toLong()
                        is String -> vRaw.toLongOrNull()
                        else -> null
                    }
                    val uId = when (uRaw) {
                        is Number -> uRaw.toLong()
                        is String -> uRaw.toLongOrNull()
                        else -> null
                    }
                    if (vId != null && vId > 0 && uId != null && uId > 0) vId to uId else null
                }
            }
        }
    }
}
