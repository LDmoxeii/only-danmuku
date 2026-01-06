package edu.only4.danmuku.application.commands.video_encrypt

import com.only.engine.exception.KnownException
import com.only.engine.json.misc.JsonUtils
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_hls_encrypt_key.SVideoHlsEncryptKey
import edu.only4.danmuku.domain.aggregates.video_post.enums.EncryptMethod
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.enums.EncryptKeyStatus
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.factory.VideoHlsEncryptKeyFactory
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.util.Base64
import java.util.UUID

/**
 * 批量生成清晰度独立 HLS key（同批次 keyVersion）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
object GenerateVideoPostQualityKeysCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val videoPostId = request.videoPostId
            val fileIndex = request.fileIndex
            val qualities = request.qualities
                .mapNotNull { it.trim().takeIf(String::isNotBlank) }
                .distinct()
            if (qualities.isEmpty()) {
                throw KnownException.illegalArgument("qualities")
            }
            val method = runCatching { EncryptMethod.valueOf(request.method) }
                .getOrNull() ?: throw KnownException.illegalArgument("method")

            val keyVersion = nextKeyVersion(videoPostId, fileIndex)
            val payloads = qualities.map { quality ->
                val keyBytes = generateRandomBytes(request.keyBytes)
                val keyId = UUID.randomUUID().toString()
                val keyCiphertextBase64 = Base64.getEncoder().encodeToString(keyBytes)
                val ivHex = generateRandomBytes(16).joinToString("") { "%02x".format(it) }
                val keyUriTemplate = "/video/enc/key?keyId=$keyId&quality=$quality&token=__TOKEN__"

                Mediator.factories.create(
                    VideoHlsEncryptKeyFactory.Payload(
                        videoPostId = videoPostId,
                        fileIndex = fileIndex,
                        quality = quality,
                        keyId = keyId,
                        keyCiphertext = keyCiphertextBase64,
                        ivHex = ivHex,
                        keyVersion = keyVersion,
                        method = method,
                        keyUriTemplate = keyUriTemplate,
                        expireTime = null,
                        status = EncryptKeyStatus.ACTIVE,
                        remark = null
                    )
                )
                KeyPayload(
                    quality = quality,
                    keyId = keyId,
                    keyPlainHex = keyBytes.joinToString("") { "%02x".format(it) },
                    keyCiphertextBase64 = keyCiphertextBase64,
                    ivHex = ivHex,
                    keyUriTemplate = keyUriTemplate
                )
            }
            Mediator.uow.save()

            val keysJson = JsonUtils.toJsonString(payloads) ?: "[]"
            return Response(
                keyVersion = keyVersion,
                keysJson = keysJson
            )
        }

    }

    private fun generateRandomBytes(size: Int): ByteArray {
        val length = if (size > 0) size else 16
        return ByteArray(length).also { SecureRandom().nextBytes(it) }
    }

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

    data class KeyPayload(
        val quality: String,
        val keyId: String,
        val keyPlainHex: String,
        val keyCiphertextBase64: String,
        val ivHex: String?,
        val keyUriTemplate: String
    )

    data class Request(
        val videoPostId: Long,
        val fileIndex: Int,
        val qualities: List<String>,
        val method: String = "HLS_AES_128",
        val keyBytes: Int = 16
    ) : RequestParam<Response>

    data class Response(
        val keyVersion: Int,
        val keysJson: String
    )
}
