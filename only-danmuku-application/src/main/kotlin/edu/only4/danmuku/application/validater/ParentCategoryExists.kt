package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.category.CategoryExistsByIdQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 校验父分类是否存在：
 * - parentId == 0L 直接通过（顶级）
 * - parentId != 0L 调用查询判断分类是否存在
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ParentCategoryExists.Validator::class])
@MustBeDocumented
annotation class ParentCategoryExists(
    val message: String = "父分类不存在",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
) {
    class Validator : ConstraintValidator<ParentCategoryExists, Long> {
        override fun isValid(value: Long?, context: ConstraintValidatorContext): Boolean {
            val pid = value ?: return true
            if (pid == 0L) return true

            val resp = Mediator.queries.send(CategoryExistsByIdQry.Request(categoryId = pid))
            return resp.exists
        }
    }
}

