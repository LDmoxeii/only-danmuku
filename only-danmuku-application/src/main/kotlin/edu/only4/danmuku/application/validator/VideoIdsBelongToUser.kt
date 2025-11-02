package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.video.CheckVideosOwnershipQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 校验：请求中的视频ID均属于当前用户
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [VideoIdsBelongToUser.Validator::class])
@MustBeDocumented
annotation class VideoIdsBelongToUser(
    val message: String = "包含不属于当前用户的视频",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val userIdField: String = "userId",
    val videoIdsField: String = "videoIds",
) {
    class Validator : ConstraintValidator<VideoIdsBelongToUser, Any> {
        private lateinit var userIdField: String
        private lateinit var videoIdsField: String

        override fun initialize(constraintAnnotation: VideoIdsBelongToUser) {
            userIdField = constraintAnnotation.userIdField
            videoIdsField = constraintAnnotation.videoIdsField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true
            val props = value::class.memberProperties.associateBy { it.name }
            val userId = (props[userIdField]?.getter?.call(value) as? Long) ?: return true
            val raw = props[videoIdsField]?.getter?.call(value) as? String? ?: return true
            val ids = raw.split(',', '，').mapNotNull { it.trim().trim('"').takeIf { s -> s.isNotEmpty() }?.toLongOrNull() }.distinct()
            if (ids.isEmpty()) return true

            val result = Mediator.queries.send(
                CheckVideosOwnershipQry.Request(
                    userId = userId,
                    videoIds = ids
                )
            )
            return result.allOwned
        }
    }
}

