package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.video_comment.GetCommentByIdQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 验证被回复评论存在且属于同一个视频
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ReplyCommentExists.Validator::class])
@MustBeDocumented
annotation class ReplyCommentExists(
    val message: String = "被回复的评论不存在或不属于该视频",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val videoIdField: String = "videoId",
    val replyCommentIdField: String = "replyCommentId",
) {
    class Validator : ConstraintValidator<ReplyCommentExists, Any> {
        private lateinit var videoIdField: String
        private lateinit var replyCommentIdField: String

        override fun initialize(constraintAnnotation: ReplyCommentExists) {
            videoIdField = constraintAnnotation.videoIdField
            replyCommentIdField = constraintAnnotation.replyCommentIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }
            val videoId = props[videoIdField]?.getter?.call(value) as? Long ?: return true
            val replyCommentId = props[replyCommentIdField]?.getter?.call(value) as? Long?

            // 一级评论无需验证
            val targetCommentId = replyCommentId ?: return true

            val comment = runCatching {
                Mediator.queries.send(GetCommentByIdQry.Request(commentId = targetCommentId))
            }.getOrNull() ?: return false

            return comment.videoId == videoId
        }
    }
}

