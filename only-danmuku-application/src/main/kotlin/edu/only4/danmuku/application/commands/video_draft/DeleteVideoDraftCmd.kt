package edu.only4.danmuku.application.commands.video_draft

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import org.springframework.stereotype.Service

/**
 * 删除视频草稿
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object DeleteVideoDraftCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 删除视频草稿（包括级联删除附属的文件草稿）
            // 由于配置了 orphanRemoval = true 和 CascadeType.ALL，会自动删除 videoFileDrafts
            Mediator.repositories.remove(
                SVideoPost.predicate { it.id eq request.videoId }
            )

            Mediator.uow.save()

            return Response()
        }
    }

    data class Request(
        val videoId: Long,
    ) : RequestParam<Response>

    class Response
}
