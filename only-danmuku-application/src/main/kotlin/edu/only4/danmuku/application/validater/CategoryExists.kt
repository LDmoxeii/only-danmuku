package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.category.CategoryExistsByIdQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 校验分类是否存在：
 * - 值为 0（顶级）时视为通过（用于父分类场景）
 * - 其他非空值通过查询判断是否存在
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [CategoryExists.Validator::class])
@MustBeDocumented
annotation class CategoryExists(
    val message: String = "分类不存在",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {
    class Validator : ConstraintValidator<CategoryExists, Long> {
        override fun isValid(value: Long?, context: ConstraintValidatorContext): Boolean {
            val id = value ?: return true
            if (id == 0L) return true
            val resp = Mediator.queries.send(CategoryExistsByIdQry.Request(categoryId = id))
            return resp.exists
        }
    }
}

