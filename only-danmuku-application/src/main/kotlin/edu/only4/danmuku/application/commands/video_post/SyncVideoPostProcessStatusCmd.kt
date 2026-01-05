package edu.only4.danmuku.application.commands.video_post

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus

import org.springframework.stereotype.Service

/**
 * 处理聚合事件驱动：更新稿件整体状态
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object SyncVideoPostProcessStatusCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
                success = TODO("set success")
            )
        }

    }

    data class Request(
        val videoPostId: Long,
        val targetStatus: VideoStatus,
        val duration: Int?,
        val failReason: String?
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true
    )
}
