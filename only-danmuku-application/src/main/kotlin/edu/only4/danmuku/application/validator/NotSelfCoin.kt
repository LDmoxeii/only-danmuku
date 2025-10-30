package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.video.GetVideoInfoQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 验证UP主不能给自己的视频投币
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NotSelfCoin.Validator::class])
@MustBeDocumented
annotation class NotSelfCoin(
    val message: String = "不能给自己的视频投币",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val userIdField: String = "customerId",
    val videoIdField: String = "videoId"
) {
    class Validator : ConstraintValidator<NotSelfCoin, Any> {
        private lateinit var userIdField: String
        private lateinit var videoIdField: String

        override fun initialize(constraintAnnotation: NotSelfCoin) {
            this.userIdField = constraintAnnotation.userIdField
            this.videoIdField = constraintAnnotation.videoIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }
            val userId = (props[userIdField]?.getter?.call(value) as? Long) ?: return true
            val videoId = (props[videoIdField]?.getter?.call(value) as? Long) ?: return true

            // 查询视频信息，获取UP主ID
            val video = runCatching {
                Mediator.queries.send(GetVideoInfoQry.Request(videoId))
            }.getOrNull() ?: return false

            // 验证当前用户不是视频所有者
            return video.userId != userId
        }
    }
}
