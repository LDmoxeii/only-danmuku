package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application._share.config.properties.SysSettingProperties
import edu.only4.danmuku.application.queries.customer_profile.CheckUserCoinBalanceQry
import edu.only4.danmuku.application.queries.customer_profile.GetCustomerProfileQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 校验：当请求修改昵称时，是否具备足够的硬币余额
 * - 若未修改昵称或昵称为空，则放行
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NicknameChangeAllowed.Validator::class])
@MustBeDocumented
annotation class NicknameChangeAllowed(
    val message: String = "硬币余额不足，无法修改昵称",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val userIdField: String = "customerId",
    val nicknameField: String = "nickName",
) {
    class Validator : ConstraintValidator<NicknameChangeAllowed, Any> {
        private lateinit var userIdField: String
        private lateinit var nicknameField: String

        override fun initialize(constraintAnnotation: NicknameChangeAllowed) {
            userIdField = constraintAnnotation.userIdField
            nicknameField = constraintAnnotation.nicknameField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }
            val userId = (props[userIdField]?.getter?.call(value) as? Long) ?: return true
            val newNickname = (props[nicknameField]?.getter?.call(value) as? String?)?.trim().orEmpty()
            if (newNickname.isBlank()) return true

            // 获取当前昵称，未修改则放行
            val current = runCatching {
                Mediator.queries.send(GetCustomerProfileQry.Request(customerId = userId))
            }.getOrNull() ?: return true
            if (current.nickName == newNickname) return true

            // 读取修改昵称所需消耗
            val sys = Mediator.ioc.getBean(SysSettingProperties::class.java)
            val required = sys.renameNicknameCoinCost.coerceAtLeast(0)
            if (required == 0) return true

            val balance = Mediator.queries.send(
                CheckUserCoinBalanceQry.Request(
                    userId = userId,
                    requiredAmount = required
                )
            )
            return balance.sufficient
        }
    }
}

