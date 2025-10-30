package edu.only4.danmuku.application.commands.user

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.domain._share.meta.user.SUser
import org.springframework.stereotype.Service

/**
 * 关联档案
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object RelationProfileCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val user = Mediator.repo.findOne(
                SUser.predicateById(request.userId)
            ).get()

            user.bindingRelationship(request.profileId)

            Mediator.uow.save()
        }
    }

    data class Request(
        val userId: Long,
        val profileId: Long,
    ) : RequestParam<Unit>
}
