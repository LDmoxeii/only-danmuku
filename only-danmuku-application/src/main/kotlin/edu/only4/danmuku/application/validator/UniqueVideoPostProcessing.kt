package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.video_post_processing.UniqueVideoPostProcessingQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueVideoPostProcessing.Validator::class])
@MustBeDocumented
annotation class UniqueVideoPostProcessing(
    val message: String = "唯一性校验未通过",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val videoPostIdField: String = "videoPostId",
    val videoPostProcessingIdField: String = "videoPostProcessingId",
) {
    class Validator : ConstraintValidator<UniqueVideoPostProcessing, Any> {
        private lateinit var videoPostIdProperty: String
        private lateinit var videoPostProcessingIdProperty: String

        override fun initialize(constraintAnnotation: UniqueVideoPostProcessing) {
            videoPostIdProperty = constraintAnnotation.videoPostIdField
            videoPostProcessingIdProperty = constraintAnnotation.videoPostProcessingIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }

            // 读取唯一字段值
            val videoPostId = props[videoPostIdProperty]?.getter?.call(value) as? Long?

            // 读取排除 ID
            val excludeId = props[videoPostProcessingIdProperty]?.getter?.call(value) as? Long

            // 所有参数均有值（字符串非空）才进行校验
            val allPresent =
                (videoPostId != null)
            if (!allPresent) return true

            val result = runCatching {
                Mediator.queries.send(
                    UniqueVideoPostProcessingQry.Request(
                        videoPostId = videoPostId!!,
                        excludeVideoPostProcessingId = excludeId,
                    )
                )
            }.getOrNull() ?: return false

            return !result.exists
        }
    }
}
