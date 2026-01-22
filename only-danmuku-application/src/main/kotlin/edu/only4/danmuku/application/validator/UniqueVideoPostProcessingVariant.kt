package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.video_post_processing.UniqueVideoPostProcessingVariantQry
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
@Constraint(validatedBy = [UniqueVideoPostProcessingVariant.Validator::class])
@MustBeDocumented
annotation class UniqueVideoPostProcessingVariant(
    val message: String = "唯一性校验未通过",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val parentIdField: String = "parentId",
    val qualityField: String = "quality",
    val videoPostProcessingVariantIdField: String = "videoPostProcessingVariantId",
) {
    class Validator : ConstraintValidator<UniqueVideoPostProcessingVariant, Any> {
        private lateinit var parentIdProperty: String
        private lateinit var qualityProperty: String
        private lateinit var videoPostProcessingVariantIdProperty: String

        override fun initialize(constraintAnnotation: UniqueVideoPostProcessingVariant) {
            parentIdProperty = constraintAnnotation.parentIdField
            qualityProperty = constraintAnnotation.qualityField
            videoPostProcessingVariantIdProperty = constraintAnnotation.videoPostProcessingVariantIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }

            // 读取唯一字段值
            val parentId = props[parentIdProperty]?.getter?.call(value) as? Long?
            val quality = props[qualityProperty]?.getter?.call(value) as? String?
            val qualityTrimmed = quality?.trim()

            // 读取排除 ID
            val excludeId = props[videoPostProcessingVariantIdProperty]?.getter?.call(value) as? Long

            // 所有参数均有值（字符串非空）才进行校验
            val allPresent =
                (parentId != null) &&
                (qualityTrimmed != null && qualityTrimmed.isNotBlank())
            if (!allPresent) return true

            val result = runCatching {
                Mediator.queries.send(
                    UniqueVideoPostProcessingVariantQry.Request(
                        parentId = parentId!!,
                        quality = qualityTrimmed!!,
                        excludeVideoPostProcessingVariantId = excludeId,
                    )
                )
            }.getOrNull() ?: return false

            return !result.exists
        }
    }
}
