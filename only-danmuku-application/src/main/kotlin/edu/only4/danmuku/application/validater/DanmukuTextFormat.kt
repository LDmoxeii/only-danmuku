package edu.only4.danmuku.application.validater

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 校验弹幕文本、颜色、模式等参数格式
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DanmukuTextFormat.Validator::class])
@MustBeDocumented
annotation class DanmukuTextFormat(
    val message: String = "弹幕参数格式不正确",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val textField: String = "text",
    val colorField: String = "color",
    val modeField: String = "mode",
    val timeField: String = "time",
) {
    class Validator : ConstraintValidator<DanmukuTextFormat, Any> {
        private lateinit var textField: String
        private lateinit var colorField: String
        private lateinit var modeField: String
        private lateinit var timeField: String

        override fun initialize(constraintAnnotation: DanmukuTextFormat) {
            textField = constraintAnnotation.textField
            colorField = constraintAnnotation.colorField
            modeField = constraintAnnotation.modeField
            timeField = constraintAnnotation.timeField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }

            val text = props[textField]?.getter?.call(value) as? String
            if (text != null && text.length > 200) {
                context.disableDefaultConstraintViolation()
                context.buildConstraintViolationWithTemplate("弹幕内容不能超过200个字符")
                    .addPropertyNode(textField).addConstraintViolation()
                return false
            }

            val color = props[colorField]?.getter?.call(value) as? String
            if (color != null && !COLOR_PATTERN.matches(color)) {
                context.disableDefaultConstraintViolation()
                context.buildConstraintViolationWithTemplate("弹幕颜色格式错误，应为#RRGGBB")
                    .addPropertyNode(colorField).addConstraintViolation()
                return false
            }

            val mode = props[modeField]?.getter?.call(value) as? Int
            if (mode != null && mode !in validModes) {
                context.disableDefaultConstraintViolation()
                context.buildConstraintViolationWithTemplate("弹幕模式不支持")
                    .addPropertyNode(modeField).addConstraintViolation()
                return false
            }

            val time = props[timeField]?.getter?.call(value) as? Int
            if (time != null && time < 0) {
                context.disableDefaultConstraintViolation()
                context.buildConstraintViolationWithTemplate("弹幕时间必须大于等于0")
                    .addPropertyNode(timeField).addConstraintViolation()
                return false
            }

            return true
        }

        companion object {
            private val COLOR_PATTERN = Regex("^#([0-9a-fA-F]{6})$")
            private val validModes = setOf(0, 1, 2)
        }
    }
}

