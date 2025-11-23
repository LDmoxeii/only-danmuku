package edu.only4.danmuku.application.commands.video_file_post

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import edu.only4.danmuku.domain.aggregates.video_file_post.factory.VideoFilePostFactory
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 基于上传会话为稿件创建/绑定 VideoFilePost 记录（初始转码中）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
object CreateVideoFilePostCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.repositories.findOne(SVideoPost.predicateById(request.videoId))
                .getOrNull() ?: throw KnownException("视频稿件不存在: ${request.videoId}")

            val file = Mediator.factories.create(
                VideoFilePostFactory.Payload(
                    uploadId = request.uploadId,
                    customerId = request.customerId,
                    videoId = request.videoId,
                    fileIndex = request.fileIndex,
                    fileName = request.fileName,
                    fileSize = request.fileSize,
                    duration = request.duration
                )
            )

            Mediator.uow.save()

            return Response(
                videoFilePostId = file.id
            )
        }

    }

    data class Request(
        val uploadId: Long,
        val customerId: Long,
        val videoId: Long,
        val fileIndex: Int,
        val fileName: String? = null,
        val fileSize: Long? = null,
        val duration: Int? = null,
    ) : RequestParam<Response>

    data class Response(
        val videoFilePostId: Long
    )
}
