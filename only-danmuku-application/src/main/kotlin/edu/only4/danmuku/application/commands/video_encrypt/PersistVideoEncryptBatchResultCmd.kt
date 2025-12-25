package edu.only4.danmuku.application.commands.video_encrypt

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_file_post.SVideoFilePost
import edu.only4.danmuku.domain._share.meta.video_hls_encrypt_key.SVideoHlsEncryptKey
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.EncryptMethod
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
            val file = Mediator.repositories.findOne(
                SVideoFilePost.predicateById(request.videoFilePostId)
            ).getOrNull() ?: throw KnownException("分P不存在: ${request.videoFilePostId}")

            val method = runCatching { EncryptMethod.valueOf(request.encryptMethod) }
                .getOrNull() ?: throw KnownException.illegalArgument("encryptMethod")

            val keyDbId = resolveKeyDbId(
                fileId = file.id,
                keyVersion = request.keyVersion
            )
            file.applyEncryptResult(
                success = request.success,
                method = method,
                keyDbId = keyDbId ?: file.encryptKeyId,
                failReason = request.failReason
            )
            Mediator.uow.save()

            return Response(
                encryptStatus = file.encryptStatus.code
            )
        }

    }

    private fun resolveKeyDbId(fileId: Long, keyVersion: Int): Long? {
        val found = Mediator.repositories.findFirst(
            SVideoHlsEncryptKey.predicate { schema ->
                schema.all(
                    schema.fileId.eq(fileId),
                    schema.keyVersion.eq(keyVersion)
                )
            }
        )
        return found.getOrNull()?.id
    }

    data class Request(
        val videoFilePostId: Long,
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
