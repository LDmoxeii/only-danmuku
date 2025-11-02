package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.video_draft.GetVideoPostInfoQry
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

@Target(AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [VideoPostEditableStatus.Validator::class])
@MustBeDocumented
annotation class VideoPostEditableStatus(
    val message: String = "视频草稿状态不可编辑",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val videoIdField: String = "videoId",
    val userIdField: String = "customerId",
) {

    class Validator : ConstraintValidator<VideoPostEditableStatus, Any> {
        private lateinit var videoIdProperty: String
        private lateinit var userIdProperty: String

        override fun initialize(constraintAnnotation: VideoPostEditableStatus) {
            videoIdProperty = constraintAnnotation.videoIdField
            userIdProperty = constraintAnnotation.userIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            val (videoId, userId) = extractIds(value) ?: return true

            val resp = runCatching {
                Mediator.queries.send(
                    GetVideoPostInfoQry.Request(
                        videoPostId = videoId,
                        userId = userId
                    )
                )
            }.getOrNull() ?: return false

            val statusEnum = VideoStatus.valueOf(resp.videoInfo.status)
            return statusEnum != VideoStatus.REVIEW_PASSED
        }

        private fun extractIds(source: Any?): Pair<Long, Long>? {
            if (source == null) return null
            return when (source) {
                is Number -> null // 仅数字无法区分 userId
                is String -> null // 同上
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
