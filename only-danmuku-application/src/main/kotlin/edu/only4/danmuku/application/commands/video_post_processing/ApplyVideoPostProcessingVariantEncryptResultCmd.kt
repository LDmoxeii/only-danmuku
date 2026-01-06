package edu.only4.danmuku.application.commands.video_post_processing

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_post_processing.SVideoPostProcessing
import edu.only4.danmuku.domain.aggregates.video_post.enums.EncryptMethod
import kotlin.jvm.optionals.getOrNull

import org.springframework.stereotype.Service

/**
 * 回写单个清晰度加密结果并汇总状态
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
object ApplyVideoPostProcessingVariantEncryptResultCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val method = runCatching { EncryptMethod.valueOf(request.encryptMethod) }
                .getOrNull() ?: throw KnownException.illegalArgument("encryptMethod")
            val processing = Mediator.repositories.findFirst(
                SVideoPostProcessing.predicate { schema ->
                    schema.videoPostId.eq(request.videoPostId)
                }
            ).getOrNull() ?: throw KnownException("处理聚合不存在: ${request.videoPostId}")

            processing.applyVariantEncryptResult(
                fileIndex = request.fileIndex,
                quality = request.quality,
                success = request.success,
                method = method,
                keyVersion = request.keyVersion,
                playlistPath = request.playlistPath,
                segmentPrefix = request.segmentPrefix,
                failReason = request.failReason
            )
            Mediator.uow.save()

            return Response(
                success = request.success
            )
        }

    }

    data class Request(
        val videoPostId: Long,
        val fileIndex: Int,
        val quality: String,
        val success: Boolean,
        val encryptMethod: String = "HLS_AES_128",
        val keyVersion: Int,
        val playlistPath: String?,
        val segmentPrefix: String?,
        val failReason: String?
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true
    )
}
