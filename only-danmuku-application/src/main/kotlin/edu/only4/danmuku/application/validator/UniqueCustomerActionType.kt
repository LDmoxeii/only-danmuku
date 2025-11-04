package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator

import edu.only4.danmuku.application.queries.customer_action.UniqueCustomerActionTypeQry
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload

import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 用户行为 点赞、评论;
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/04
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueCustomerActionType.Validator::class])
@MustBeDocumented
annotation class UniqueCustomerActionType(
    val message: String = "唯一性校验未通过",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val videoIdField: String = "videoId",
    val commentIdField: String = "commentId",
    val actionTypeField: String = "actionType",
    val customerIdField: String = "customerId",
    val customerActionIdField: String = "customerActionId",
) {
    class Validator : ConstraintValidator<UniqueCustomerActionType, Any> {
        private lateinit var videoIdProperty: String
        private lateinit var commentIdProperty: String
        private lateinit var actionTypeProperty: String
        private lateinit var customerIdProperty: String
        private lateinit var customerActionIdProperty: String

        override fun initialize(constraintAnnotation: UniqueCustomerActionType) {
            videoIdProperty = constraintAnnotation.videoIdField
            commentIdProperty = constraintAnnotation.commentIdField
            actionTypeProperty = constraintAnnotation.actionTypeField
            customerIdProperty = constraintAnnotation.customerIdField
            customerActionIdProperty = constraintAnnotation.customerActionIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }

            // 读取唯一字段值
            val videoId = props[videoIdProperty]?.getter?.call(value) as? Long?
            val commentId = props[commentIdProperty]?.getter?.call(value) as? Long??
            val actionType = props[actionTypeProperty]?.getter?.call(value) as? ActionType?
            val customerId = props[customerIdProperty]?.getter?.call(value) as? Long?

            // 读取排除 ID
            val excludeId = props[customerActionIdProperty]?.getter?.call(value) as? Long

            // 所有参数均有值（字符串非空）才进行校验
            val allPresent =
                (videoId != null) &&
                (commentId != null) &&
                (actionType != null) &&
                (customerId != null)
            if (!allPresent) return true

            val result = runCatching {
                Mediator.queries.send(
                    UniqueCustomerActionTypeQry.Request(
                        videoId = videoId!!,
                        commentId = commentId!!,
                        actionType = actionType!!,
                        customerId = customerId!!,
                        excludeCustomerActionId = excludeId,
                    )
                )
            }.getOrNull() ?: return false

            return !result.exists
        }
    }
}
