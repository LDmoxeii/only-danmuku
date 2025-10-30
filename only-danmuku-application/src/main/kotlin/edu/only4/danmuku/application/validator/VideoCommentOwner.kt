package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.video_comment.GetCommentByIdQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 校验评论归属，确保操作者为该视频作者（或后台管理员）
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [VideoCommentOwner.Validator::class])
@MustBeDocumented
annotation class VideoCommentOwner(
    val message: String = "无权置顶该评论",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val commentIdField: String = "commentId",
    val operatorIdField: String = "operatorId",
) {
    class Validator : ConstraintValidator<VideoCommentOwner, Any> {
        private lateinit var commentIdField: String
        private lateinit var operatorIdField: String

        override fun initialize(constraintAnnotation: VideoCommentOwner) {
            commentIdField = constraintAnnotation.commentIdField
            operatorIdField = constraintAnnotation.operatorIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }
            val commentId = (props[commentIdField]?.getter?.call(value) as? Long) ?: return true
            val operatorId = props[operatorIdField]?.getter?.call(value) as? Long?

            // 后台管理员（operatorId = null）默认通过
            val expectedOwner = runCatching {
                Mediator.queries.send(GetCommentByIdQry.Request(commentId))
            }.getOrNull()?.videoOwnerId ?: return false

            return operatorId == null || operatorId == expectedOwner
        }
    }
}

