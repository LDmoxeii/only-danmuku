package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.category.CategoryExistsByCodeQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 验证分类编码唯一性，适配创建/更新等多种场景
 *
 * 默认读取 `code` 和 `categoryId` 字段，可通过注解参数自定义
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueCategoryCode.Validator::class])
@MustBeDocumented
annotation class UniqueCategoryCode(
    val message: String = "分类编码已被其他分类使用",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val codeField: String = "code",
    val categoryIdField: String = "categoryId",
) {

    /**
     * 通过读取请求中的栏目编码与当前实体 ID 进行唯一性校验
     */
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
            val code = (props[codeProperty]?.getter?.call(value) as? String)?.trim().orEmpty()
            if (code.isBlank()) return true

            val excludeId = categoryIdProperty
                .takeIf { it.isNotBlank() }
                ?.let { props[it]?.getter?.call(value) as? Long? }
                ?.takeIf { it != 0L }

            val queryResult = runCatching {
                Mediator.queries.send(
                    CategoryExistsByCodeQry.Request(
                        code = code,
                        excludeCategoryId = excludeId
                    )
                )
            }.getOrNull() ?: return false

            return !queryResult.exists
        }
    }
}
