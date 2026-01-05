package edu.only4.danmuku.application.commands.video_encrypt

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import edu.only4.danmuku.domain.aggregates.video_post.enums.EncryptMethod
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 批量加密结果落库（更新状态/批次版本）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
object PersistVideoEncryptBatchResultCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val videoPost = Mediator.repositories.findOne(
                SVideoPost.predicateById(request.videoPostId)
            ).getOrNull() ?: throw KnownException("稿件不存在: ${request.videoPostId}")
            val file = videoPost.videoFilePosts.firstOrNull { it.fileIndex == request.fileIndex }
                ?: throw KnownException("分P不存在: videoPostId=${request.videoPostId}, fileIndex=${request.fileIndex}")

            val method = runCatching { EncryptMethod.valueOf(request.encryptMethod) }
                .getOrNull() ?: throw KnownException.illegalArgument("encryptMethod")

            val outputPrefix = resolveOutputPrefix(request.encryptedMasterPath)
            file.applyEncryptResult(
                success = request.success,
                method = method,
                keyVersion = request.keyVersion,
                outputPrefix = if (request.success) outputPrefix else null
            )
            Mediator.uow.save()

            return Response(
                encryptStatus = file.encryptStatus.code
            )
        }

    }

    private fun resolveOutputPrefix(masterPath: String?): String? {
        if (masterPath.isNullOrBlank()) return null
        val trimmed = masterPath.trim()
        return if (trimmed.endsWith("/master.m3u8")) {
            trimmed.removeSuffix("/master.m3u8")
        } else {
            trimmed.substringBeforeLast("/", trimmed)
        }.trimEnd('/')
    }

    data class Request(
        val videoPostId: Long,
        val fileIndex: Int,
        val success: Boolean,
        val encryptMethod: String = "HLS_AES_128",
        val keyVersion: Int,
        val encryptedMasterPath: String?,
        val encryptedVariants: String?,
        val failReason: String?
    ) : RequestParam<Response>

    data class Response(
        val encryptStatus: Int
    )
}
