package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.customer_action.GetUserActionByVideoQry
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 验证同一视频只能投币一次
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NotDuplicateCoin.Validator::class])
@MustBeDocumented
annotation class NotDuplicateCoin(
    val message: String = "该视频已投过币",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val userIdField: String = "customerId",
    val videoIdField: String = "videoId"
) {
    class Validator : ConstraintValidator<NotDuplicateCoin, Any> {
        private lateinit var userIdField: String
        private lateinit var videoIdField: String

        override fun initialize(constraintAnnotation: NotDuplicateCoin) {
            this.userIdField = constraintAnnotation.userIdField
            this.videoIdField = constraintAnnotation.videoIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) return true

            val props = value::class.memberProperties.associateBy { it.name }
            val userId = (props[userIdField]?.getter?.call(value) as? Long) ?: return true
            val videoId = (props[videoIdField]?.getter?.call(value) as? Long) ?: return true

            // 查询用户是否已对该视频投过币
            val result = Mediator.queries.send(
                GetUserActionByVideoQry.Request(
                    customerId = userId,
                    videoId = videoId,
                    actionType = ActionType.COIN_VIDEO.code
                )
            )

            // 如果已投过币，验证失败
            return !result.hasAction
        }
    }
}
