package edu.only4.danmuku.application.commands.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import com.only.engine.exception.KnownException
import edu.only4.danmuku.domain._share.meta.video_file_post.SVideoFilePost
import edu.only4.danmuku.domain._share.meta.video_hls_encrypt_key.SVideoHlsEncryptKey
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.EncryptMethod
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.EncryptStatus
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull
import kotlin.jvm.optionals.getOrNull

/**
 * 根据加密 CLI 结果更新 VideoFilePost 状态，落密钥/目录元数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object PersistVideoEncryptResultCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val file = Mediator.repositories.findOne(SVideoFilePost.predicateById(request.videoFilePostId))
                .getOrNull() ?: throw KnownException("分P不存在: ${request.videoFilePostId}")

            val method = EncryptMethod.valueOfOrNull(request.encryptMethod)
                ?: throw KnownException.illegalArgument("encryptMethod")

            val keyDbId = resolveKeyDbId(
                fileId = file.id,
                keyId = request.keyId,
                keyVersion = request.keyVersion,
                keyQuality = request.keyQuality
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

        private fun resolveKeyDbId(fileId: Long, keyId: String?, keyVersion: Int?, keyQuality: String?): Long? {
            if (keyId.isNullOrBlank()) return null
            val found = Mediator.repositories.findFirst(
                SVideoHlsEncryptKey.predicate { schema ->
                    val parts = listOfNotNull(
                        schema.fileId.eq(fileId),
                        schema.keyId.eq(keyId),
                        keyVersion?.let { v -> schema.keyVersion.eq(v) },
                        keyQuality?.let { q -> schema.quality.eq(q) }
                    )
                    schema.all(*parts.toTypedArray())
                }
            )
            return found.getOrNull()?.id
        }
    }

    data class Request(
        val videoFilePostId: Long,
        val success: Boolean,
        val encryptMethod: Int = 1,
        val keyId: String?,
        val keyVersion: Int?,
        val keyQuality: String?,
        val encryptedMasterPath: String?,
        val encryptedVariants: String?,
        val failReason: String?
    ) : RequestParam<Response>

    data class Response(
        val encryptStatus: Int
    )
}
