package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator

import edu.only4.danmuku.application.queries.video_hls_key_token.UniqueVideoHlsKeyTokenQry

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
@Constraint(validatedBy = [UniqueVideoHlsKeyToken.Validator::class])
@MustBeDocumented
annotation class UniqueVideoHlsKeyToken(
    val message: String = "唯一性校验未通过",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val tokenHashField: String = "tokenHash",
    val videoHlsKeyTokenIdField: String = "videoHlsKeyTokenId",
) {
    class Validator : ConstraintValidator<UniqueVideoHlsKeyToken, Any> {
        private lateinit var tokenHashProperty: String
        private lateinit var videoHlsKeyTokenIdProperty: String

        override fun initialize(constraintAnnotation: UniqueVideoHlsKeyToken) {
            tokenHashProperty = constraintAnnotation.tokenHashField
            videoHlsKeyTokenIdProperty = constraintAnnotation.videoHlsKeyTokenIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }

            // 读取唯一字段值
            val tokenHash = props[tokenHashProperty]?.getter?.call(value) as? String?
            val tokenHashTrimmed = tokenHash?.trim()

            // 读取排除 ID
            val excludeId = props[videoHlsKeyTokenIdProperty]?.getter?.call(value) as? Long

            // 所有参数均有值（字符串非空）才进行校验
            val allPresent =
                (tokenHashTrimmed != null && tokenHashTrimmed.isNotBlank())
            if (!allPresent) return true

            val result = runCatching {
                Mediator.queries.send(
                    UniqueVideoHlsKeyTokenQry.Request(
                        tokenHash = tokenHashTrimmed!!,
                        excludeVideoHlsKeyTokenId = excludeId,
                    )
                )
            }.getOrNull() ?: return false

            return !result.exists
        }
    }
}
