package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.category.CategoryExistsByIdQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

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
            val id = value ?: return true
            if (id == 0L) return true
            val resp = Mediator.queries.send(CategoryExistsByIdQry.Request(categoryId = id))
            return resp.exists
        }
    }
}

