package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator

import edu.only4.danmuku.application.queries.video_quality_policy.UniqueVideoQualityPolicyVideoIdFileIndexQualityQry

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
@Constraint(validatedBy = [UniqueVideoQualityPolicyVideoIdFileIndexQuality.Validator::class])
@MustBeDocumented
annotation class UniqueVideoQualityPolicyVideoIdFileIndexQuality(
    val message: String = "唯一性校验未通过",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val videoIdField: String = "videoId",
    val fileIndexField: String = "fileIndex",
    val qualityField: String = "quality",
    val videoQualityPolicyIdField: String = "videoQualityPolicyId",
) {
    class Validator : ConstraintValidator<UniqueVideoQualityPolicyVideoIdFileIndexQuality, Any> {
        private lateinit var videoIdProperty: String
        private lateinit var fileIndexProperty: String
        private lateinit var qualityProperty: String
        private lateinit var videoQualityPolicyIdProperty: String

        override fun initialize(constraintAnnotation: UniqueVideoQualityPolicyVideoIdFileIndexQuality) {
            videoIdProperty = constraintAnnotation.videoIdField
            fileIndexProperty = constraintAnnotation.fileIndexField
            qualityProperty = constraintAnnotation.qualityField
            videoQualityPolicyIdProperty = constraintAnnotation.videoQualityPolicyIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }

            // 读取唯一字段值
            val videoId = props[videoIdProperty]?.getter?.call(value) as? Long?
            val fileIndex = props[fileIndexProperty]?.getter?.call(value) as? Int?
            val quality = props[qualityProperty]?.getter?.call(value) as? String?
            val qualityTrimmed = quality?.trim()

            // 读取排除 ID
            val excludeId = props[videoQualityPolicyIdProperty]?.getter?.call(value) as? Long

            // 所有参数均有值（字符串非空）才进行校验
            val allPresent =
                (videoId != null) &&
                (fileIndex != null) &&
                (qualityTrimmed != null && qualityTrimmed.isNotBlank())
            if (!allPresent) return true

            val result = runCatching {
                Mediator.queries.send(
                    UniqueVideoQualityPolicyVideoIdFileIndexQualityQry.Request(
                        videoId = videoId!!,
                        fileIndex = fileIndex!!,
                        quality = qualityTrimmed!!,
                        excludeVideoQualityPolicyId = excludeId,
                    )
                )
            }.getOrNull() ?: return false

            return !result.exists
        }
    }
}
