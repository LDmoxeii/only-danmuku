package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.category.CategoryExistsByIdQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 校验分类是否存在（用于删除、更新等严格场景）。
 * - 0 或 null 视为非法，返回 false
 * - 其他情况调用查询确认存在
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [CategoryMustExist.Validator::class])
@MustBeDocumented
annotation class CategoryMustExist(
    val message: String = "分类不存在",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {
    class Validator : ConstraintValidator<CategoryMustExist, Long> {
        override fun isValid(value: Long?, context: ConstraintValidatorContext): Boolean {
            val id = value ?: return false
            if (id <= 0L) return false
            val resp = Mediator.queries.send(CategoryExistsByIdQry.Request(categoryId = id))
            return resp.exists
        }
    }
}

