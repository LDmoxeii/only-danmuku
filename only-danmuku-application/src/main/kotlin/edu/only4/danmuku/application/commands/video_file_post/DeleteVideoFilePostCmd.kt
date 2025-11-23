package edu.only4.danmuku.application.commands.video_file_post

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_file_post.SVideoFilePost
import org.springframework.stereotype.Service

/**
 * 删除已移除的分P记录，触发稿件状态刷新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
object DeleteVideoFilePostCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.repositories.remove(
                SVideoFilePost.predicateById(request.videoFilePostId)
            )
            Mediator.uow.save()

            return Response(
            )
        }

    }

    data class Request(
        val videoFilePostId: Long,
    ) : RequestParam<Response>

    class Response(
    )
}
