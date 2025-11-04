package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator

import edu.only4.danmuku.application.queries.video_post.UniqueVideoFilePostUploadIdQry

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload

import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 视频文件信息;
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/04
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueVideoFilePostUploadId.Validator::class])
@MustBeDocumented
annotation class UniqueVideoFilePostUploadId(
    val message: String = "唯一性校验未通过",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val uploadIdField: String = "uploadId",
    val customerIdField: String = "customerId",
    val videoFilePostIdField: String = "videoFilePostId",
) {
    class Validator : ConstraintValidator<UniqueVideoFilePostUploadId, Any> {
        private lateinit var uploadIdProperty: String
        private lateinit var customerIdProperty: String
        private lateinit var videoFilePostIdProperty: String

        override fun initialize(constraintAnnotation: UniqueVideoFilePostUploadId) {
            uploadIdProperty = constraintAnnotation.uploadIdField
            customerIdProperty = constraintAnnotation.customerIdField
            videoFilePostIdProperty = constraintAnnotation.videoFilePostIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }

            // 读取唯一字段值
            val uploadId = props[uploadIdProperty]?.getter?.call(value) as? Long?
            val customerId = props[customerIdProperty]?.getter?.call(value) as? Long?

            // 读取排除 ID
            val excludeId = props[videoFilePostIdProperty]?.getter?.call(value) as? Long

            // 所有参数均有值（字符串非空）才进行校验
            val allPresent =
                (uploadId != null) &&
                (customerId != null)
            if (!allPresent) return true

            val result = runCatching {
                Mediator.queries.send(
                    UniqueVideoFilePostUploadIdQry.Request(
                        uploadId = uploadId!!,
                        customerId = customerId!!,
                        excludeVideoFilePostId = excludeId,
                    )
                )
            }.getOrNull() ?: return false

            return !result.exists
        }
    }
}
