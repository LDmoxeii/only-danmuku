package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator

import edu.only4.danmuku.application.queries.category.UniqueCategoryCodeQry

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
@Constraint(validatedBy = [UniqueCategoryCode.Validator::class])
@MustBeDocumented
annotation class UniqueCategoryCode(
    val message: String = "唯一性校验未通过",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val codeField: String = "code",
    val categoryIdField: String = "categoryId",
) {
    class Validator : ConstraintValidator<UniqueCategoryCode, Any> {
        private lateinit var codeProperty: String
        private lateinit var categoryIdProperty: String

        override fun initialize(constraintAnnotation: UniqueCategoryCode) {
            codeProperty = constraintAnnotation.codeField
            categoryIdProperty = constraintAnnotation.categoryIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }

            // 读取唯一字段值
            val code = props[codeProperty]?.getter?.call(value) as? String?
            val codeTrimmed = code?.trim()

            // 读取排除 ID
            val excludeId = props[categoryIdProperty]?.getter?.call(value) as? Long

            // 所有参数均有值（字符串非空）才进行校验
            val allPresent =
                (codeTrimmed != null && codeTrimmed.isNotBlank())
            if (!allPresent) return true

            val result = runCatching {
                Mediator.queries.send(
                    UniqueCategoryCodeQry.Request(
                        code = codeTrimmed!!,
                        excludeCategoryId = excludeId,
                    )
                )
            }.getOrNull() ?: return false

            return !result.exists
        }
    }
}
