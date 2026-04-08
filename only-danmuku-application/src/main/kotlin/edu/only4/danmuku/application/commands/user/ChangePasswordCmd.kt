package edu.only4.danmuku.application.commands.user

import com.only.engine.error.CommonErrors
import com.only.engine.exception.AppException
import com.only.engine.exception.BusinessException
import com.only.engine.exception.DependencyException
import com.only.engine.exception.RequestException
import com.only.engine.exception.SystemException
import edu.only4.danmuku.domain.shared.error.DanmukuBusinessErrors
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.user.SUser
import org.springframework.stereotype.Service

/**
 * 用户修改登录密码
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
object ChangePasswordCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val user = Mediator.repositories
                .findOne(SUser.predicateById(request.userId))
                .orElseThrow { BusinessException(DanmukuBusinessErrors.RESOURCE_NOT_FOUND, "用户不存在") }

            val isOldPasswordCorrect = user.verifyPassword(request.oldPassword)
            if (!isOldPasswordCorrect) {
                throw BusinessException(DanmukuBusinessErrors.STATE_INVALID, "原密码不正确")
            }

            user.changePassword(request.newPassword)

            Mediator.uow.save()

            return Response()
        }
    }

    class Request(
        val userId: Long,
        val oldPassword: String,
        val newPassword: String,
    ) : RequestParam<Response>

    class Response
}
