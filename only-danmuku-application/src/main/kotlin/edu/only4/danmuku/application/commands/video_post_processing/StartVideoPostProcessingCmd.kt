package edu.only4.danmuku.application.commands.video_post_processing

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 初始化稿件处理聚合（文件清单）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object StartVideoPostProcessingCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
            )
        }

    }

    data class Request(
        val videoPostId: Long,
        val fileList: List<VideoPostProcessingFileSpec>
    ) : RequestParam<Response>

    data class VideoPostProcessingFileSpec(
        val uploadId: Long,
        val fileIndex: Int,
        val fileName: String?,
        val fileSize: Long?,
        val duration: Int?
    )

    class Response
}
