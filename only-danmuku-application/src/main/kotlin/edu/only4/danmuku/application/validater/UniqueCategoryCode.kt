package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.category.UpdateCategoryInfoCmd
import edu.only4.danmuku.application.queries.category.CategoryExistsByCodeQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 验证分类编码唯一性（更新场景，排除自身）
 *
 * 用于更新分类时验证新编码是否与其他分类冲突
 * 注意：这是类级别的验证注解，需要同时访问 categoryId 和 code
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueCategoryCode.Validator::class])
@MustBeDocumented
annotation class UniqueCategoryCode(
    val message: String = "分类编码已被其他分类使用",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
) {

    /**
     * 分类编码唯一性验证器（更新场景）
     *
     * 验证分类编码是否已被其他分类使用（排除当前分类自身）
     */
    class Validator : ConstraintValidator<UniqueCategoryCode, UpdateCategoryInfoCmd.Request> {

        override fun isValid(request: UpdateCategoryInfoCmd.Request?, context: ConstraintValidatorContext): Boolean {
            // 空对象不验证
            if (request == null) {
                return true
            }

            // 空值由 @NotBlank 等其他注解处理
            if (request.code.isBlank()) {
                return true
            }

            // 通过 CQRS 查询检查分类编码是否已存在
            val queryResult = Mediator.queries.send(
                CategoryExistsByCodeQry.Request(
                    code = request.code,
                    excludeCategoryId = request.categoryId  // 排除当前分类自身
                )
            )

            return !queryResult.exists
        }
    }
}
