package edu.only4.danmuku.application.commands.video_file_post

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import com.only.engine.exception.KnownException
import edu.only4.danmuku.domain._share.meta.video_file_post.SVideoFilePost
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 根据转码结果更新单个 VideoFilePost 的转码状态/输出路径/时长
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
object UpdateVideoFilePostTranscodeResultCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val file = Mediator.repositories.findOne(
                SVideoFilePost.predicateById(request.videoFilePostId)
            ).getOrNull() ?: throw KnownException("分P不存在: ${request.videoFilePostId}")

            file.applyTranscodeResult(
                success = request.success,
                duration = request.duration,
                fileSize = request.fileSize,
                filePath = request.outputPath,
                failReason = request.failReason
            )
            Mediator.uow.save()

            return Response(
                videoFilePostId = file.id,
                transferResult = file.transferResult.code
            )
        }

    }

    data class Request(
        val videoFilePostId: Long,
        val success: Boolean,
        val outputPath: String? = null,
        val duration: Int? = null,
        val fileSize: Long? = null,
        val failReason: String? = null,
    ) : RequestParam<Response>

    data class Response(
        val videoFilePostId: Long,
        val transferResult: Int,
    )
}
