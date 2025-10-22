package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.video_comment.CommentExistsByIdQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 校验评论是否存在（通过查询器，而非仓储）
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [CommentExists.Validator::class])
@MustBeDocumented
annotation class CommentExists(
    val message: String = "评论不存在",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {
    class Validator : ConstraintValidator<CommentExists, Long> {
        override fun isValid(value: Long?, context: ConstraintValidatorContext): Boolean {
            val id = value ?: return true
            val resp = Mediator.queries.send(CommentExistsByIdQry.Request(commentId = id))
            return resp.exists
        }
    }
}
