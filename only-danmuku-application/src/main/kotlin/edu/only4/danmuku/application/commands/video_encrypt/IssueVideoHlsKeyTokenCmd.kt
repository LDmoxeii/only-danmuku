package edu.only4.danmuku.application.commands.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import com.only.engine.exception.KnownException
import edu.only4.danmuku.domain.aggregates.video_hls_key_token.VideoHlsKeyToken
import edu.only4.danmuku.domain.aggregates.video_hls_key_token.enums.EncryptTokenStatus
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.UUID


/**
 * 签发用于播放的短时 token，绑定 fileId+keyId+keyVersion
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object IssueVideoHlsKeyTokenCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val fileId = request.videoFilePostId ?: request.videoFileId
                ?: throw KnownException.illegalArgument("videoFilePostId")

            val now = System.currentTimeMillis()
            val expireAt = now + request.expireSeconds * 1000L
            val token = UUID.randomUUID().toString().replace("-", "")
            val tokenHash = sha256(token)

            Mediator.uow.persist(
                VideoHlsKeyToken(
                    fileId = fileId,
                    keyId = request.keyId,
                    keyVersion = request.keyVersion,
                    allowedQualities = request.allowedQualities,
                    tokenHash = tokenHash,
                    audience = request.audience,
                    expireTime = expireAt,
                    maxUse = request.maxUse.coerceAtLeast(1),
                    usedCount = 0,
                    status = EncryptTokenStatus.VALID
                )
            )
            Mediator.uow.save()

            return Response(
                token = token,
                expireAt = expireAt,
                allowedQualities = request.allowedQualities
            )
        }

        private fun sha256(input: String): String {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(input.toByteArray(StandardCharsets.UTF_8))
            return hash.joinToString("") { "%02x".format(it) }
        }
    }

    data class Request(
        val videoFilePostId: Long?,
        val videoFileId: Long?,
        val keyId: String,
        val keyVersion: Int,
        val audience: String?,
        val expireSeconds: Int = 600,
        val maxUse: Int = 5,
        val allowedQualities: String?
    ) : RequestParam<Response>

    data class Response(
        val token: String,
        val expireAt: Long,
        val allowedQualities: String?
    )
}
