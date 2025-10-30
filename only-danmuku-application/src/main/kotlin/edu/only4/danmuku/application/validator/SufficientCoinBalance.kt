package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.customer_profile.CheckUserCoinBalanceQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 验证用户硬币余额充足
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SufficientCoinBalance.Validator::class])
@MustBeDocumented
annotation class SufficientCoinBalance(
    val message: String = "硬币余额不足",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val userIdField: String = "customerId",
    val coinCountField: String = "coinCount"
) {
    class Validator : ConstraintValidator<SufficientCoinBalance, Any> {
        private lateinit var userIdField: String
        private lateinit var coinCountField: String

        override fun initialize(constraintAnnotation: SufficientCoinBalance) {
            this.userIdField = constraintAnnotation.userIdField
            this.coinCountField = constraintAnnotation.coinCountField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }
            val userId = (props[userIdField]?.getter?.call(value) as? Long) ?: return true
            val coinCount = (props[coinCountField]?.getter?.call(value) as? Int) ?: return true

            // 查询用户硬币余额
            val result = Mediator.queries.send(
                CheckUserCoinBalanceQry.Request(
                    userId = userId,
                    requiredAmount = coinCount
                )
            )

            // 验证余额是否充足
            return result.sufficient
        }
    }
}
