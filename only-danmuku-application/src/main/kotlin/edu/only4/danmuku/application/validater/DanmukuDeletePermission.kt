package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.domain._share.meta.video.SVideo
import edu.only4.danmuku.domain._share.meta.video_danmuku.SVideoDanmuku
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 校验弹幕删除权限（UP主或管理员）
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DanmukuDeletePermission.Validator::class])
@MustBeDocumented
annotation class DanmukuDeletePermission(
    val message: String = "无权限删除该弹幕",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val danmukuIdField: String = "danmukuId",
    val operatorIdField: String = "operatorId",
) {
    class Validator : ConstraintValidator<DanmukuDeletePermission, Any> {
        private lateinit var danmukuIdField: String
        private lateinit var operatorIdField: String

        override fun initialize(constraintAnnotation: DanmukuDeletePermission) {
            danmukuIdField = constraintAnnotation.danmukuIdField
            operatorIdField = constraintAnnotation.operatorIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }
            val danmukuId = (props[danmukuIdField]?.getter?.call(value) as? Long) ?: return true
            val operatorId = props[operatorIdField]?.getter?.call(value) as? Long?

            // 管理员（operatorId == null）直接通过
            if (operatorId == null) return true

            val danmuku = Mediator.repositories.findFirst(
                SVideoDanmuku.predicateById(danmukuId),
                persist = false
            ).getOrNull() ?: return true

            val video = Mediator.repositories.findFirst(
                SVideo.predicateById(danmuku.videoId),
                persist = false
            ).getOrNull() ?: return false

            return video.customerId == operatorId
        }
    }
}

