package edu.only4.danmuku.application.subscribers.domain.user

import com.only.engine.misc.ServletUtils
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.user_behavior.RecordAbnormalOperationCmd
import edu.only4.danmuku.application.commands.user_behavior.RecordLoginLogCmd
import edu.only4.danmuku.application.queries.user_behavior.GetRecentPasswordFailureCountQry
import edu.only4.danmuku.domain.aggregates.user.events.PasswordInputFailedDomainEvent
import edu.only4.danmuku.domain.aggregates.user_abnormal_operation_log.enums.AbnormalOpType
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginResult
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginType
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 密码输入失败领域事件（用于触发异常操作统计）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
@Service
class PasswordInputFailedDomainEventSubscriber {

    companion object {
        private const val FAILURE_THRESHOLD: Long = 5
        private const val WINDOW_SECONDS: Long = 30 * 60
    }

    @EventListener(PasswordInputFailedDomainEvent::class)
    fun on(event: PasswordInputFailedDomainEvent) {
        val occurTime = event.occurTime
        val ip = event.ip ?: ServletUtils.getClientIP().orEmpty()

        val loginLogResponse = Mediator.commands.send(
            RecordLoginLogCmd.Request(
                userId = event.entity.id,
                userType = event.entity.type,
                loginName = event.loginName,
                loginType = LoginType.PASSWORD,
                result = LoginResult.FAILURE,
                ip = ip,
                userAgent = event.userAgent,
                reason = event.reason ?: "密码错误",
                occurTime = occurTime
            )
        )

        val failureCount = Mediator.queries.send(
            GetRecentPasswordFailureCountQry.Request(
                userId = event.entity.id,
                loginName = event.loginName,
                windowSeconds = WINDOW_SECONDS,
                now = occurTime
            )
        ).failureCount

        if (failureCount >= FAILURE_THRESHOLD) {
            Mediator.commands.send(
                RecordAbnormalOperationCmd.Request(
                    userId = event.entity.id,
                    userType = event.entity.type,
                    opType = AbnormalOpType.PASSWORD_FAIL_TOO_MANY_TIMES,
                    ip = ip,
                    occurTime = occurTime,
                    description = "密码失败次数过多",
                    extra = """{"lastLoginLogId":${loginLogResponse.logId},"failureCount":$failureCount}"""
                )
            )
        }
    }
}
