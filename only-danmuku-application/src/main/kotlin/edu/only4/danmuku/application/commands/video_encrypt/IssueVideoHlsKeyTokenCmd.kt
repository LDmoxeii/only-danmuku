package edu.only4.danmuku.application.commands.video_encrypt

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.queries.video_encrypt.GetLatestVideoHlsKeyVersionQry
import edu.only4.danmuku.domain.aggregates.video_hls_key_token.factory.VideoHlsKeyTokenFactory
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*


/**
 * 签发用于播放的短时 token，绑定 videoPostId+fileIndex+keyVersion+allowedQualities
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object IssueVideoHlsKeyTokenCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val keyVersion = request.keyVersion ?: Mediator.queries.send(
                GetLatestVideoHlsKeyVersionQry.Request(
                    videoPostId = request.videoPostId,
                    fileIndex = request.fileIndex
                )
            ).keyVersion ?: throw KnownException("未找到可用密钥版本")

            val now = System.currentTimeMillis()
            val expireAt = now + request.expireSeconds * 1000L
            val token = UUID.randomUUID().toString().replace("-", "")
            val tokenHash = sha256(token)

            Mediator.factories.create(
                VideoHlsKeyTokenFactory.Payload(
                    videoPostId = request.videoPostId,
                    videoId = request.videoId,
                    fileIndex = request.fileIndex,
                    keyVersion = keyVersion,
                    allowedQualities = request.allowedQualities,
                    tokenHash = tokenHash,
                    audience = request.audience,
                    expireTime = expireAt,
                    maxUse = request.maxUse.coerceAtLeast(1),
                )
            )

            Mediator.uow.save()

            return Response(
                token = token,
                expireAt = expireAt,
                keyVersion = keyVersion,
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
        val videoPostId: Long,
        val videoId: Long,
        val fileIndex: Int,
        val keyVersion: Int?,
        val audience: String?,
        val expireSeconds: Int = 600,
        val maxUse: Int = 30,
        val allowedQualities: String?
    ) : RequestParam<Response>

    data class Response(
        val token: String,
        val expireAt: Long,
        val keyVersion: Int?,
        val allowedQualities: String?
    )
}
