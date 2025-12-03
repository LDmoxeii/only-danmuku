package edu.only4.danmuku.adapter.application.distributed.clients.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestHandler
import com.only.engine.exception.KnownException
import edu.only4.danmuku.application.distributed.clients.video_encrypt.RotateVideoHlsKeyCli
import edu.only4.danmuku.domain._share.meta.video_hls_encrypt_key.SVideoHlsEncryptKey
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.VideoHlsEncryptKey
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.enums.EncryptKeyStatus
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.util.Base64
import java.util.UUID

/**
 * 防腐层：触发新密钥生成与 m3u8 重写（可重用 EncryptHlsWithKey），用于密钥轮换
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class RotateVideoHlsKeyCliHandler : RequestHandler<RotateVideoHlsKeyCli.Request, RotateVideoHlsKeyCli.Response> {
    override fun exec(request: RotateVideoHlsKeyCli.Request): RotateVideoHlsKeyCli.Response {
        return runCatching {
            val latest = Mediator.repositories.find(
                SVideoHlsEncryptKey.predicate { it.fileId.eq(request.videoFilePostId) }
            ).maxByOrNull { it.keyVersion } ?: throw KnownException("未找到可轮换的密钥")

            val newVersion = latest.keyVersion + 1
            val keyBytes = ByteArray(16).also { SecureRandom().nextBytes(it) }
            val keyCiphertext = Base64.getEncoder().encodeToString(keyBytes)
            val ivHex = ByteArray(16).also { SecureRandom().nextBytes(it) }.joinToString("") { b -> "%02x".format(b) }
            val keyId = UUID.randomUUID().toString()

            Mediator.uow.persist(
                VideoHlsEncryptKey(
                    fileId = latest.fileId,
                    quality = latest.quality,
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

            RotateVideoHlsKeyCli.Response(
                newKeyVersion = newVersion,
                failReason = null
            )
        }.getOrElse {
            RotateVideoHlsKeyCli.Response(
                newKeyVersion = 0,
                failReason = it.message
            )
        }
    }
}

