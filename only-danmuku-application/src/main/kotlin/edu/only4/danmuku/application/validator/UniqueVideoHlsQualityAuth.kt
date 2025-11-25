package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator

import edu.only4.danmuku.application.queries.video_hls_quality_auth.UniqueVideoHlsQualityAuthQry

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
@Constraint(validatedBy = [UniqueVideoHlsQualityAuth.Validator::class])
@MustBeDocumented
annotation class UniqueVideoHlsQualityAuth(
    val message: String = "唯一性校验未通过",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val fileIdField: String = "fileId",
    val qualityField: String = "quality",
    val videoHlsQualityAuthIdField: String = "videoHlsQualityAuthId",
) {
    class Validator : ConstraintValidator<UniqueVideoHlsQualityAuth, Any> {
        private lateinit var fileIdProperty: String
        private lateinit var qualityProperty: String
        private lateinit var videoHlsQualityAuthIdProperty: String

        override fun initialize(constraintAnnotation: UniqueVideoHlsQualityAuth) {
            fileIdProperty = constraintAnnotation.fileIdField
            qualityProperty = constraintAnnotation.qualityField
            videoHlsQualityAuthIdProperty = constraintAnnotation.videoHlsQualityAuthIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }

            // 读取唯一字段值
            val fileId = props[fileIdProperty]?.getter?.call(value) as? Long?
            val quality = props[qualityProperty]?.getter?.call(value) as? String?
            val qualityTrimmed = quality?.trim()

            // 读取排除 ID
            val excludeId = props[videoHlsQualityAuthIdProperty]?.getter?.call(value) as? Long

            // 所有参数均有值（字符串非空）才进行校验
            val allPresent =
                (fileId != null) &&
                (qualityTrimmed != null && qualityTrimmed.isNotBlank())
            if (!allPresent) return true

            val result = runCatching {
                Mediator.queries.send(
                    UniqueVideoHlsQualityAuthQry.Request(
                        fileId = fileId!!,
                        quality = qualityTrimmed!!,
                        excludeVideoHlsQualityAuthId = excludeId,
                    )
                )
            }.getOrNull() ?: return false

            return !result.exists
        }
    }
}
