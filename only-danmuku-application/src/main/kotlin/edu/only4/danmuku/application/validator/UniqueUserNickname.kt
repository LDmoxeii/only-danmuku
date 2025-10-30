package edu.only4.danmuku.application.validator

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.queries.user.CheckNicknameExistsQry
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * 验证昵称唯一性的注解
 *
 * 默认读取 `nickName`、`userId` 字段，可在注解参数中自定义以支持更新场景的自我排除
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueUserNickname.Validator::class])
@MustBeDocumented
annotation class UniqueUserNickname(
    val message: String = "昵称已被使用",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val nicknameField: String = "nickName",
    val userIdField: String = "userId",
) {

    /**
     * 昵称唯一性验证器
     */
    class Validator : ConstraintValidator<UniqueUserNickname, Any> {
        private lateinit var nicknameProperty: String
        private lateinit var userIdProperty: String

        override fun initialize(constraintAnnotation: UniqueUserNickname) {
            nicknameProperty = constraintAnnotation.nicknameField
            userIdProperty = constraintAnnotation.userIdField
        }

        override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
            if (value == null) {
                return true
            }

            return when (value) {
                is String -> isNicknameUnique(value, excludeUserId = null)
                else -> {
                    val props = value::class.memberProperties.associateBy { it.name }
                    val nickname = props[nicknameProperty]?.getter?.call(value) as? String?
                    val excludeUserId = userIdProperty
                        .takeIf { it.isNotBlank() }
                        ?.let { props[it]?.getter?.call(value) as? Long? }
                        ?.takeIf { it != 0L }

                    isNicknameUnique(nickname, excludeUserId)
                }
            }
        }

        private fun isNicknameUnique(nickname: String?, excludeUserId: Long?): Boolean {
            val normalizedNickname = nickname?.trim().orEmpty()
            if (normalizedNickname.isBlank()) {
                return true
            }

            val queryResult = runCatching {
                Mediator.queries.send(
                    CheckNicknameExistsQry.Request(
                        nickName = normalizedNickname,
                        excludeUserId = excludeUserId
                    )
                )
            }.getOrNull() ?: return false

            return !queryResult.exists
        }
    }
}
