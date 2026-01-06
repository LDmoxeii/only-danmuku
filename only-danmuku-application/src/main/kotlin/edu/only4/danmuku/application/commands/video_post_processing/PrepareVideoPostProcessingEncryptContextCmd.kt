package edu.only4.danmuku.application.commands.video_post_processing

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_hls_encrypt_key.SVideoHlsEncryptKey
import edu.only4.danmuku.domain._share.meta.video_post_processing.SVideoPostProcessing
import edu.only4.danmuku.domain.aggregates.video_post.enums.EncryptMethod
import kotlin.jvm.optionals.getOrNull

import org.springframework.stereotype.Service

/**
 * 准备单分P加密上下文（分配 keyVersion 并返回输出路径）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
object PrepareVideoPostProcessingEncryptContextCmd {

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

            val file = processing.videoPostProcessingFiles.firstOrNull { it.fileIndex == request.fileIndex }
                ?: throw KnownException("处理文件不存在: fileIndex=${request.fileIndex}")
            val keyVersion = file.encryptKeyVersion ?: nextKeyVersion(request.videoPostId, request.fileIndex)

            val context = processing.prepareEncryptContext(
                fileIndex = request.fileIndex,
                method = method,
                keyVersion = keyVersion
            )
            Mediator.uow.save()

            return Response(
                keyVersion = context.keyVersion,
                transcodeOutputPrefix = context.transcodeOutputPrefix,
                encryptOutputDir = context.encryptOutputDir
            )
        }

    }

    data class Request(
        val videoPostId: Long,
        val fileIndex: Int,
        val encryptMethod: String = "HLS_AES_128"
    ) : RequestParam<Response>

    data class Response(
        val keyVersion: Int,
        val transcodeOutputPrefix: String?,
        val encryptOutputDir: String?
    )

    private fun nextKeyVersion(videoPostId: Long, fileIndex: Int): Int {
        val keys = Mediator.repositories.find(
            SVideoHlsEncryptKey.predicate { schema ->
                schema.all(
                    schema.videoPostId.eq(videoPostId),
                    schema.fileIndex.eq(fileIndex)
                )
            }
        )
        val maxVersion = keys.maxOfOrNull { it.keyVersion } ?: 0
        return maxVersion + 1
    }
}
