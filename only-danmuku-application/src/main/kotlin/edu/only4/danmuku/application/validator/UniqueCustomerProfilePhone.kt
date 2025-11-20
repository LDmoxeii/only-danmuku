package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator

import edu.only4.danmuku.application.queries.customer_profile.UniqueCustomerProfilePhoneQry

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload

import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueCustomerProfilePhone.Validator::class])
@MustBeDocumented
annotation class UniqueCustomerProfilePhone(
    val message: String = "唯一性校验未通过",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val phoneField: String = "phone",
    val customerProfileIdField: String = "customerProfileId",
) {
    class Validator : ConstraintValidator<UniqueCustomerProfilePhone, Any> {
        private lateinit var phoneProperty: String
        private lateinit var customerProfileIdProperty: String

        override fun initialize(constraintAnnotation: UniqueCustomerProfilePhone) {
            phoneProperty = constraintAnnotation.phoneField
            customerProfileIdProperty = constraintAnnotation.customerProfileIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }

            // 读取唯一字段值
            val phone = props[phoneProperty]?.getter?.call(value) as? String?
            val phoneTrimmed = phone?.trim()

            // 读取排除 ID
            val excludeId = props[customerProfileIdProperty]?.getter?.call(value) as? Long

            // 所有参数均有值（字符串非空）才进行校验
            val allPresent =
                (phoneTrimmed != null && phoneTrimmed.isNotBlank())
            if (!allPresent) return true

            val result = runCatching {
                Mediator.queries.send(
                    UniqueCustomerProfilePhoneQry.Request(
                        phone = phoneTrimmed!!,
                        excludeCustomerProfileId = excludeId,
                    )
                )
            }.getOrNull() ?: return false

            return !result.exists
        }
    }
}
