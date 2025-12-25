package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application._share.config.properties.SysSettingProperties
import edu.only4.danmuku.application._share.constants.Constants
import edu.only4.danmuku.domain._share.meta.video_file_upload_session.SVideoFileUploadSession
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.enums.UploadStatus
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import org.springframework.web.multipart.MultipartFile
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 分片上传参数校验（类级别）
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidateUploadChunk.Validator::class])
@MustBeDocumented
annotation class ValidateUploadChunk(
    val message: String = "分片上传参数非法",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val customerIdField: String = "customerId",
    val uploadIdField: String = "uploadId",
    val chunkIndexField: String = "chunkIndex",
    val chunkFileField: String = "chunkFile",
    val chunkSizeField: String = "chunkSize",
) {
    class Validator : ConstraintValidator<ValidateUploadChunk, Any> {
        private lateinit var customerIdProp: String
        private lateinit var uploadIdProp: String
        private lateinit var chunkIndexProp: String
        private lateinit var chunkFileProp: String
        private lateinit var chunkSizeProp: String

        override fun initialize(constraintAnnotation: ValidateUploadChunk) {
            customerIdProp = constraintAnnotation.customerIdField
            uploadIdProp = constraintAnnotation.uploadIdField
            chunkIndexProp = constraintAnnotation.chunkIndexField
            chunkFileProp = constraintAnnotation.chunkFileField
            chunkSizeProp = constraintAnnotation.chunkSizeField
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

            val chunkIndex = when (val raw = props[chunkIndexProp]?.getter?.call(value)) {
                is Number -> raw.toInt()
                is String -> raw.toIntOrNull()
                else -> null
            } ?: return violation("分片索引非法")

            val chunkFile = props[chunkFileProp]?.getter?.call(value) as? MultipartFile
            val chunkSize = when (val raw = props[chunkSizeProp]?.getter?.call(value)) {
                is Number -> raw.toLong()
                is String -> raw.toLongOrNull()
                else -> null
            }
            val actualSize = chunkFile?.size ?: chunkSize ?: return violation("分片大小不能为空")

            val sys = Mediator.ioc.getBean(SysSettingProperties::class.java)
            val limitBytes = sys.videoSize.toLong() * Constants.MB_SIZE

            val session = Mediator.repositories.findFirst(
                SVideoFileUploadSession.predicateById(uploadId)
            ).getOrNull() ?: return violation("文件不存在请重新上传")

            if (session.customerId != customerId) {
                return violation("没有权限操作该上传")
            }

            if (session.status == UploadStatus.ABORTED || session.status == UploadStatus.EXPIRED) {
                return violation("上传会话不可用")
            }

            if (chunkIndex < 0 || chunkIndex > session.chunks - 1) {
                return violation("分片索引非法")
            }
            if ((chunkIndex - 1) > session.chunkIndex) {
                return violation("分片索引非法")
            }

            val newSize = (session.fileSize ?: 0L) + actualSize
            if (newSize > limitBytes) {
                return violation("文件超过最大文件限制")
            }

            return true
        }
    }
}

