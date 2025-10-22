package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.domain._share.meta.video_danmuku.SVideoDanmuku
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.KClass

/**
 * 校验弹幕是否存在
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DanmukuExists.Validator::class])
@MustBeDocumented
annotation class DanmukuExists(
    val message: String = "弹幕不存在",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {
    class Validator : ConstraintValidator<DanmukuExists, Long> {
        override fun isValid(value: Long?, context: ConstraintValidatorContext): Boolean {
            val danmukuId = value ?: return true
            val danmuku = Mediator.repositories.findFirst(
                SVideoDanmuku.predicateById(danmukuId),
                persist = false
            ).getOrNull()
            return danmuku != null
        }
    }
}

