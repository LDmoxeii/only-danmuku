package edu.only4.danmuku.application.commands.user

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.domain._share.meta.user.SUser
import org.springframework.stereotype.Service

/**
 * 更新登录信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object UpdateLoginInfoCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val user = Mediator.repositories
                .findOne(SUser.predicateById(request.userId))
                .orElseThrow { KnownException("用户不存在") }

            user.updateLoginInfo(
                loginTime = System.currentTimeMillis() / 1000L,
                loginIp = request.loginIp
            )

            Mediator.uow.save()
        }
    }

    class Request(
        val userId: Long,
        val loginIp: String,
    ) : RequestParam<Unit>
}
