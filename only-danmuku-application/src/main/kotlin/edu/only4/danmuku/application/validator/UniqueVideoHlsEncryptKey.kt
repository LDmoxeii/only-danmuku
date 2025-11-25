package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator

import edu.only4.danmuku.application.queries.video_hls_encrypt_key.UniqueVideoHlsEncryptKeyQry

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
@Constraint(validatedBy = [UniqueVideoHlsEncryptKey.Validator::class])
@MustBeDocumented
annotation class UniqueVideoHlsEncryptKey(
    val message: String = "唯一性校验未通过",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val fileIdField: String = "fileId",
    val keyIdField: String = "keyId",
    val keyVersionField: String = "keyVersion",
    val qualityField: String = "quality",
    val videoHlsEncryptKeyIdField: String = "videoHlsEncryptKeyId",
) {
    class Validator : ConstraintValidator<UniqueVideoHlsEncryptKey, Any> {
        private lateinit var fileIdProperty: String
        private lateinit var keyIdProperty: String
        private lateinit var keyVersionProperty: String
        private lateinit var qualityProperty: String
        private lateinit var videoHlsEncryptKeyIdProperty: String

        override fun initialize(constraintAnnotation: UniqueVideoHlsEncryptKey) {
            fileIdProperty = constraintAnnotation.fileIdField
            keyIdProperty = constraintAnnotation.keyIdField
            keyVersionProperty = constraintAnnotation.keyVersionField
            qualityProperty = constraintAnnotation.qualityField
            videoHlsEncryptKeyIdProperty = constraintAnnotation.videoHlsEncryptKeyIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }

            // 读取唯一字段值
            val fileId = props[fileIdProperty]?.getter?.call(value) as? Long?
            val keyId = props[keyIdProperty]?.getter?.call(value) as? String?
            val keyIdTrimmed = keyId?.trim()
            val keyVersion = props[keyVersionProperty]?.getter?.call(value) as? Int?
            val quality = props[qualityProperty]?.getter?.call(value) as? String?
            val qualityTrimmed = quality?.trim()

            // 读取排除 ID
            val excludeId = props[videoHlsEncryptKeyIdProperty]?.getter?.call(value) as? Long

            // 所有参数均有值（字符串非空）才进行校验
            val allPresent =
                (fileId != null) &&
                (keyIdTrimmed != null && keyIdTrimmed.isNotBlank()) &&
                (keyVersion != null) &&
                (qualityTrimmed != null && qualityTrimmed.isNotBlank())
            if (!allPresent) return true

            val result = runCatching {
                Mediator.queries.send(
                    UniqueVideoHlsEncryptKeyQry.Request(
                        fileId = fileId!!,
                        keyId = keyIdTrimmed!!,
                        keyVersion = keyVersion!!,
                        quality = qualityTrimmed!!,
                        excludeVideoHlsEncryptKeyId = excludeId,
                    )
                )
            }.getOrNull() ?: return false

            return !result.exists
        }
    }
}
