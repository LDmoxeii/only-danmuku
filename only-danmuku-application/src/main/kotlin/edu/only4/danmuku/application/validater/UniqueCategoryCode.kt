package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.category.CategoryExistsByCodeQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 验证分类编码唯一性的注解
 *
 * 用于验证分类编码是否已被使用
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueCategoryCode.Validator::class])
@MustBeDocumented
annotation class UniqueCategoryCode(
    val message: String = "分类编码已存在",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {

    /**
     * 分类编码唯一性验证器
     *
     * 验证分类编码是否已被其他分类使用
     */
    class Validator : ConstraintValidator<UniqueCategoryCode, String> {

        override fun isValid(code: String?, context: ConstraintValidatorContext): Boolean {
            // 空值由 @NotBlank 等其他注解处理
            if (code.isNullOrBlank()) {
                return true
            }

            // 通过 CQRS 查询检查分类编码是否已存在
            return !Mediator.queries.send(
                CategoryExistsByCodeQry.Request(code = code)
            ).exists
        }
    }
}
