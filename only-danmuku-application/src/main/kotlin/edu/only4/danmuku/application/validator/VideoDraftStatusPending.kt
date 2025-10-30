package edu.only4.danmuku.application.validator

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
 * 校验视频草稿状态必须为待审核（基于查询层，不直接依赖仓储）
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [VideoDraftStatusPending.Validator::class])
@MustBeDocumented
annotation class VideoDraftStatusPending(
    val message: String = "视频草稿状态必须为待审核",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val videoIdField: String = "videoId",
    val userIdField: String = "customerId",
) {

    class Validator : ConstraintValidator<VideoDraftStatusPending, Any> {
        private lateinit var videoIdProperty: String
        private lateinit var userIdProperty: String

        override fun initialize(constraintAnnotation: VideoDraftStatusPending) {
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
            return statusEnum == VideoStatus.PENDING_REVIEW
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
