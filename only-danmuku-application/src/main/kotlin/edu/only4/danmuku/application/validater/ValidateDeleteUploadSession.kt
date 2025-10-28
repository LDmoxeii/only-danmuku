package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.domain._share.meta.video_file_upload_session.SVideoFileUploadSession
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 删除上传会话参数校验（类级别）
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidateDeleteUploadSession.Validator::class])
@MustBeDocumented
annotation class ValidateDeleteUploadSession(
    val message: String = "删除上传会话参数非法",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val customerIdField: String = "customerId",
    val uploadIdField: String = "uploadId",
) {
    class Validator : ConstraintValidator<ValidateDeleteUploadSession, Any> {
        private lateinit var customerIdProp: String
        private lateinit var uploadIdProp: String

        override fun initialize(annotation: ValidateDeleteUploadSession) {
            customerIdProp = annotation.customerIdField
            uploadIdProp = annotation.uploadIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            fun violation(msg: String): Boolean {
                context.disableDefaultConstraintViolation()
                context.buildConstraintViolationWithTemplate(msg).addConstraintViolation()
                return false
            }

            val props = value::class.memberProperties.associateBy { it.name }
            val customerId = when (val raw = props[customerIdProp]?.getter?.call(value)) {
                is Number -> raw.toLong()
                is String -> raw.toLongOrNull()
                else -> null
            } ?: return violation("用户ID非法")

            val uploadId = when (val raw = props[uploadIdProp]?.getter?.call(value)) {
                is Number -> raw.toLong()
                is String -> raw.toLongOrNull()
                else -> null
            } ?: return violation("非法的 uploadId")

            val session = Mediator.repositories.findFirst(
                SVideoFileUploadSession.predicateById(uploadId)
            ).getOrNull() ?: return violation("文件不存在请重新上传")

            if (session.customerId != customerId) {
                return violation("没有权限操作该上传")
            }

            return true
        }
    }
}

