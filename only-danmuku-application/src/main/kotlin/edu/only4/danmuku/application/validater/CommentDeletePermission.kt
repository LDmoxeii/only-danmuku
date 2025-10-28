package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_comment.DeleteVideoCommentCmd
import edu.only4.danmuku.application.queries.video_comment.CommentExistsByIdQry
import edu.only4.danmuku.application.queries.video_comment.GetCommentByIdQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 删除权限校验（通过查询器，而非仓储）：
 * - operatorId == null 视为管理员，放行
 * - 否则需满足：operatorId 为视频 UP 主 或 评论作者
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [CommentDeletePermission.Validator::class])
@MustBeDocumented
annotation class CommentDeletePermission(
    val message: String = "无权限删除该评论",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {
    class Validator : ConstraintValidator<CommentDeletePermission, DeleteVideoCommentCmd.Request> {
        override fun isValid(value: DeleteVideoCommentCmd.Request?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true
            val operatorId = value.operatorId ?: return true // 管理员

            // 先判断是否存在（交由 @CommentExists 统一报错）
            val exists = Mediator.queries.send(CommentExistsByIdQry.Request(value.commentId)).exists
            if (!exists) return true

            // 查询评论信息
            val comment = Mediator.queries.send(GetCommentByIdQry.Request(value.commentId))
            return operatorId == comment.videoOwnerId || operatorId == comment.userId
        }
    }
}
