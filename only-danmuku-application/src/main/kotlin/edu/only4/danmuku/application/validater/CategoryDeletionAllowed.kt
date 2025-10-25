package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.video.CountVideosUnderCategoriesQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 校验分类可删除（分类及其所有子分类下无视频关联）。
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [CategoryDeletionAllowed.Validator::class])
@MustBeDocumented
annotation class CategoryDeletionAllowed(
    val message: String = "分类下有视频信息，无法删除",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
) {
    class Validator : ConstraintValidator<CategoryDeletionAllowed, Long> {
        override fun isValid(value: Long?, context: ConstraintValidatorContext): Boolean {
            val id = value ?: return true
            if (id <= 0L) return true
            val total = Mediator.queries.send(
                CountVideosUnderCategoriesQry.Request(categoryIds = listOf(id))
            ).totalCount
            return total == 0L
        }
    }
}

