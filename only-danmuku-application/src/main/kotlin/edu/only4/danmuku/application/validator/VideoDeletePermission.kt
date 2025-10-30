package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_draft.DeleteVideoPostCmd
import edu.only4.danmuku.application.queries.video.GetVideoInfoQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 校验是否具备删除视频的权限。
 *
 * - operatorId == null 视为后台管理员，默认放行
 * - 否则需要确保 operatorId 与视频作者一致
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [VideoDeletePermission.Validator::class])
@MustBeDocumented
annotation class VideoDeletePermission(
    val message: String = "无权限删除该视频",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {

    class Validator : ConstraintValidator<VideoDeletePermission, DeleteVideoPostCmd.Request> {
        override fun isValid(value: DeleteVideoPostCmd.Request?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true
            val operatorId = value.operatorId ?: return true
            if (value.videoId <= 0) return true

            val video = runCatching {
                Mediator.queries.send(GetVideoInfoQry.Request(value.videoId))
            }.getOrNull() ?: return true

            return video.userId == operatorId
        }
    }
}

