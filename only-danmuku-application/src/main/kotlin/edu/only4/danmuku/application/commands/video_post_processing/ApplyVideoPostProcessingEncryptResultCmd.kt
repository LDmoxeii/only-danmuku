package edu.only4.danmuku.application.commands.video_post_processing

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_post_processing.SVideoPostProcessing
import kotlin.jvm.optionals.getOrNull

import org.springframework.stereotype.Service

/**
 * 回写单个分P加密结果（处理聚合内变更）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object ApplyVideoPostProcessingEncryptResultCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val processing = Mediator.repositories.findFirst(
                SVideoPostProcessing.predicate { schema ->
                    schema.videoPostId.eq(request.videoPostId)
                }
            ).getOrNull() ?: throw KnownException("处理聚合不存在: ${request.videoPostId}")

            val outputPrefix = request.encryptedPrefix?.takeIf { it.isNotBlank() }
                ?: resolveOutputPrefix(request.encryptedMasterPath)
            processing.applyEncryptResult(
                fileIndex = request.fileIndex,
                success = request.success,
                encryptedPrefix = if (request.success) outputPrefix else null,
                failReason = request.failReason
            )

            Mediator.uow.save()

            return Response(
                success = request.success,
                failReason = request.failReason
            )
        }

    }

    data class Request(
        val videoPostId: Long,
        val fileIndex: Int,
        val success: Boolean,
        val encryptMethod: String = "HLS_AES_128",
        val keyVersion: Int,
        val encryptedPrefix: String?,
        val encryptedMasterPath: String?,
        val encryptedVariants: String?,
        val failReason: String?
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true,
        val failReason: String?
    )

    private fun resolveOutputPrefix(masterPath: String?): String? {
        if (masterPath.isNullOrBlank()) return null
        val trimmed = masterPath.trim()
        return if (trimmed.endsWith("/master.m3u8")) {
            trimmed.removeSuffix("/master.m3u8")
        } else {
            trimmed.substringBeforeLast("/", trimmed)
        }.trimEnd('/')
    }
}
