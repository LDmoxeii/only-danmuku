package edu.only4.danmuku.application.commands.video_encrypt

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.enums.EncryptKeyStatus
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.factory.VideoHlsEncryptKeyFactory
import edu.only4.danmuku.domain.aggregates.video_post.enums.EncryptMethod
import java.security.SecureRandom
import java.util.Base64
import java.util.UUID

import org.springframework.stereotype.Service

/**
 * 为单个清晰度生成密钥（转码后）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
object GenerateVideoPostQualityKeyCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val quality = request.quality.trim()
            if (quality.isBlank()) {
                throw KnownException.illegalArgument("quality")
            }
            val method = runCatching { EncryptMethod.valueOf(request.method) }
                .getOrNull() ?: throw KnownException.illegalArgument("method")

            val keyBytes = generateRandomBytes(request.keyBytes)
            val keyId = UUID.randomUUID().toString()
            val keyCiphertextBase64 = Base64.getEncoder().encodeToString(keyBytes)
            val ivHex = generateRandomBytes(16).joinToString("") { "%02x".format(it) }
            val keyUriTemplate = "/video/enc/key?keyId=$keyId&quality=$quality&token=__TOKEN__"

            val key = Mediator.factories.create(
                VideoHlsEncryptKeyFactory.Payload(
                    videoPostId = request.videoPostId,
                    fileIndex = request.fileIndex,
                    quality = quality,
                    keyId = keyId,
                    keyCiphertext = keyCiphertextBase64,
                    ivHex = ivHex,
                    keyVersion = request.keyVersion,
                    method = method,
                    keyUriTemplate = keyUriTemplate,
                    expireTime = null,
                    status = EncryptKeyStatus.ACTIVE,
                    remark = null
                )
            )
            key.onCreate()
            Mediator.uow.save()

            return Response(
                success = true
            )
        }

    }

    data class Request(
        val videoPostId: Long,
        val fileIndex: Int,
        val quality: String,
        val keyVersion: Int,
        val method: String = "HLS_AES_128",
        val keyBytes: Int = 16
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true
    )

    private fun generateRandomBytes(size: Int): ByteArray {
        val length = if (size > 0) size else 16
        return ByteArray(length).also { SecureRandom().nextBytes(it) }
    }
}
