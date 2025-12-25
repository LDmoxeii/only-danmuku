package edu.only4.danmuku.application.commands.video_encrypt

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_hls_encrypt_key.SVideoHlsEncryptKey
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.enums.EncryptKeyStatus
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.factory.VideoHlsEncryptKeyFactory
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.util.*

/**
 * 触发新密钥生成与 m3u8 重写（可重用 EncryptHlsWithKey），用于密钥轮换
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/09
 */
object RotateVideoHlsKeyCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            return runCatching {
                val keys = Mediator.repositories.find(
                    SVideoHlsEncryptKey.predicate { it.fileId.eq(request.videoFilePostId) }
                )
                if (keys.isEmpty()) {
                    throw KnownException("未找到可轮换的密钥")
                }
                val latest = keys.maxByOrNull { it.keyVersion } ?: throw KnownException("未找到可轮换的密钥")

                keys.filter { it.status == EncryptKeyStatus.ACTIVE }
                    .forEach { it.markRevoked(request.reason) }

                val newVersion = (keys.maxOfOrNull { it.keyVersion } ?: 0) + 1
                val keyBytes = ByteArray(16).also { SecureRandom().nextBytes(it) }
                val keyCiphertext = Base64.getEncoder().encodeToString(keyBytes)
                val ivHex = ByteArray(16).also { SecureRandom().nextBytes(it) }.joinToString("") { b -> "%02x".format(b) }
                val keyId = UUID.randomUUID().toString()

                Mediator.factories.create(
                    VideoHlsEncryptKeyFactory.Payload(
                        fileId = latest.fileId,
                        quality = null,
                        keyId = keyId,
                        keyCiphertext = keyCiphertext,
                        ivHex = ivHex,
                        keyVersion = newVersion,
                        method = latest.method,
                        keyUriTemplate = "/video/enc/key?keyId=$keyId&token=__TOKEN__",
                        expireTime = null,
                        status = EncryptKeyStatus.ACTIVE,
                        remark = request.reason
                    )
                )
                Mediator.uow.save()

                Response(
                    newKeyVersion = newVersion,
                    failReason = null
                )
            }.getOrElse {
                Response(
                    newKeyVersion = 0,
                    failReason = it.message
                )
            }
        }

    }

    data class Request(
        val videoFilePostId: Long,
        val videoFileId: Long? = null,
        val reason: String?
    ) : RequestParam<Response>

    data class Response(
        val newKeyVersion: Int,
        val failReason: String?
    )
}
