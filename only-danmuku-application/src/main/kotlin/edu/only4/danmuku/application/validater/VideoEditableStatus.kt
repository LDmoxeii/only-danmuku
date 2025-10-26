package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.video_draft.GetVideoDraftInfoQry
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 校验视频草稿是否处于可编辑状态（基于查询层，不直接依赖仓储）
 *
 * 可编辑：status != REVIEW_PASSED
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [VideoEditableStatus.Validator::class])
@MustBeDocumented
annotation class VideoEditableStatus(
    val message: String = "视频草稿状态不可编辑",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val videoIdField: String = "videoId",
    val userIdField: String = "customerId",
) {

    class Validator : ConstraintValidator<VideoEditableStatus, Any> {
        private lateinit var videoIdProperty: String
        private lateinit var userIdProperty: String

        override fun initialize(constraintAnnotation: VideoEditableStatus) {
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
