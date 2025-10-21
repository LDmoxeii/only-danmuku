package edu.only4.danmuku.application.validater

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.category.CategoryExistsByCodeQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 验证分类编码唯一性，适配创建/更新等多种场景
 *
 * 将该注解作用在实现 [UniqueCategoryCodeTarget] 的请求模型上，即可自动校验
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
     * 通过读取请求中的栏目编码与当前实体 ID 进行唯一性校验
     */
    class Validator : ConstraintValidator<UniqueCategoryCode, UniqueCategoryCodeTarget> {

        override fun isValid(target: UniqueCategoryCodeTarget?, context: ConstraintValidatorContext): Boolean {
            // 空值跳过
            if (target == null) {
                return true
            }

            val code = target.code
            if (code.isBlank()) {
                return true
            }

            val excludeId = target.categoryId?.takeIf { it != 0L }

            val queryResult = Mediator.queries.send(
                CategoryExistsByCodeQry.Request(
                    code = code,
                    excludeCategoryId = excludeId
                )
            )

            return !queryResult.exists
        }
    }
}

/**
 * 提供唯一性校验所需的最小信息载体
 */
interface UniqueCategoryCodeTarget {
    val code: String
    val categoryId: Long?
}
