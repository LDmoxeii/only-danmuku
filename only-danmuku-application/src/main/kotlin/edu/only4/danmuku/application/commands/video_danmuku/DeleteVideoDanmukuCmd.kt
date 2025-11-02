package edu.only4.danmuku.application.commands.video_danmuku

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.application.validator.DanmukuDeletePermission
import edu.only4.danmuku.application.validator.DanmukuExists
import edu.only4.danmuku.domain._share.meta.video_danmuku.SVideoDanmuku
import org.springframework.stereotype.Service

/**
 * 删除弹幕
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object DeleteVideoDanmukuCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            Mediator.repositories.remove(
                SVideoDanmuku.predicateById(request.danmukuId)
            )

            Mediator.uow.save()
        }
    }

    @DanmukuDeletePermission
    data class Request(
        @field:DanmukuExists
        val danmukuId: Long,
        val operatorId: Long? = null,
    ) : RequestParam<Unit>
}
