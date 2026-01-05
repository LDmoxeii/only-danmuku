package edu.only4.danmuku.application.commands.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import com.only4.cap4k.ddd.domain.repo.JpaPredicate
import com.only.engine.json.misc.JsonUtils
import edu.only4.danmuku.domain._share.meta.video_hls_encrypt_key.SVideoHlsEncryptKey
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.VideoHlsEncryptKey
import edu.only4.danmuku.domain.aggregates.video_hls_key_token.VideoHlsKeyToken
import edu.only4.danmuku.domain.aggregates.video_hls_key_token.enums.EncryptTokenStatus
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.Base64
import kotlin.jvm.optionals.getOrNull

/**
 * 校验并消费 token（递增使用次数/失效），返回可下发的明文 key
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object ConsumeVideoHlsKeyTokenCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val now = System.currentTimeMillis()
            val tokenHash = sha256(request.token)

            val token = Mediator.repositories.findFirst(
                JpaPredicate.bySpecification(VideoHlsKeyToken::class.java) { root, _, cb ->
                    cb.equal(root.get<String>("tokenHash"), tokenHash)
                }
            ).getOrNull() ?: return Response(valid = false, keyPlainHex = null, ivHex = null, failReason = "token_not_found")

            if (token.status != EncryptTokenStatus.VALID) {
                return Response(valid = false, keyPlainHex = null, ivHex = null, failReason = "token_invalid")
            }
            if (token.usedCount >= token.maxUse) {
                token.markExhausted()
                Mediator.uow.save()
                return Response(valid = false, keyPlainHex = null, ivHex = null, failReason = "token_exhausted")
            }
            if (token.expireTime <= now) {
                token.markExpired()
                Mediator.uow.save()
                return Response(valid = false, keyPlainHex = null, ivHex = null, failReason = "token_expired")
            }

            if (!qualityAllowed(token.allowedQualities, request.quality)) {
                return Response(valid = false, keyPlainHex = null, ivHex = null, failReason = "quality_not_allowed")
            }

            val key = loadKey(token, request.keyId, request.quality)
                ?: return Response(valid = false, keyPlainHex = null, ivHex = null, failReason = "key_not_found")

            val keyPlainHex = decodeBase64ToHex(key.keyCiphertext)
            token.consumeOnce()
            Mediator.uow.save()

            return Response(valid = true, keyPlainHex = keyPlainHex, ivHex = key.ivHex, failReason = null)
        }

        private fun loadKey(token: VideoHlsKeyToken, keyId: String, quality: String): VideoHlsEncryptKey? {
            return Mediator.repositories.findFirst(
                SVideoHlsEncryptKey.predicate {
                    it.all(
                        it.videoPostId.eq(token.videoPostId),
                        it.fileIndex.eq(token.fileIndex),
                        it.keyId.eq(keyId),
                        it.keyVersion.eq(token.keyVersion),
                        it.quality.eq(quality)
                    )
                }
            ).getOrNull()
        }

        private fun qualityAllowed(allowedQualities: String?, quality: String): Boolean {
            if (allowedQualities.isNullOrBlank()) return true
            val list = JsonUtils.parseArray(allowedQualities, String::class.java)
            if (list.isEmpty()) return true
            return list.contains(quality)
        }

        private fun sha256(input: String): String {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(input.toByteArray(StandardCharsets.UTF_8))
            return hash.joinToString("") { "%02x".format(it) }
        }

        private fun decodeBase64ToHex(ciphertextBase64: String): String {
            val bytes = Base64.getDecoder().decode(ciphertextBase64)
            return bytes.joinToString("") { "%02x".format(it) }
        }
    }

    data class Request(
        val token: String,
        val keyId: String,
        val quality: String
    ) : RequestParam<Response>

    data class Response(
        val valid: Boolean,
        val keyPlainHex: String?,
        val ivHex: String?,
        val failReason: String?
    )
}
