package edu.only4.danmuku.adapter.application.distributed.clients.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestHandler
import com.only.engine.exception.KnownException
import edu.only4.danmuku.application.distributed.clients.video_encrypt.GenerateVideoHlsKeyCli
import edu.only4.danmuku.domain._share.meta.video_hls_encrypt_key.SVideoHlsEncryptKey
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.EncryptMethod
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.VideoHlsEncryptKey
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.enums.EncryptKeyStatus
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.util.Base64
import java.util.UUID

/**
 * 防腐层：向 KMS 申请随机 AES-128 key + IV，生成 keyId/keyUri 占位
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class GenerateVideoHlsKeyCliHandler : RequestHandler<GenerateVideoHlsKeyCli.Request, GenerateVideoHlsKeyCli.Response> {
    override fun exec(request: GenerateVideoHlsKeyCli.Request): GenerateVideoHlsKeyCli.Response {
        val fileId = request.videoFilePostId ?: request.videoFileId
        ?: throw KnownException.illegalArgument("videoFilePostId")
        val method = EncryptMethod.valueOfOrNull(request.method)
            ?: throw KnownException.illegalArgument("method")

        val keyBytes = generateRandomBytes(request.keyBytes)
        val keyId = UUID.randomUUID().toString()
        val keyCiphertextBase64 = Base64.getEncoder().encodeToString(keyBytes)
        val ivHex = generateRandomBytes(16).joinToString("") { "%02x".format(it) }
        val nextVersion = nextKeyVersion(fileId, request.quality)
        val keyUriTemplate = "/video/enc/key?keyId=$keyId&token=__TOKEN__"

        Mediator.uow.persist(
            VideoHlsEncryptKey(
                fileId = fileId,
                quality = request.quality,
                keyId = keyId,
                keyCiphertext = keyCiphertextBase64,
                ivHex = ivHex,
                keyVersion = nextVersion,
                method = method,
                keyUriTemplate = keyUriTemplate,
                expireTime = null,
                status = EncryptKeyStatus.ACTIVE,
                remark = null
            )
        )
        Mediator.uow.save()

        return GenerateVideoHlsKeyCli.Response(
            keyId = keyId,
            keyCiphertextBase64 = keyCiphertextBase64,
            ivHex = ivHex,
            keyVersion = nextVersion,
            keyUriTemplate = keyUriTemplate
        )
    }

    private fun generateRandomBytes(size: Int): ByteArray {
        val length = if (size > 0) size else 16
        return ByteArray(length).also { SecureRandom().nextBytes(it) }
    }

    private fun nextKeyVersion(fileId: Long, quality: String?): Int {
        val keys = Mediator.repositories.find(
            SVideoHlsEncryptKey.predicate { schema ->
                schema.allNotNull(
                    schema.fileId.eq(fileId),
                    quality?.let { schema.quality.eq(it) }
                )!!
            }
        )
        val maxVersion = keys.maxOfOrNull { it.keyVersion } ?: 0
        return maxVersion + 1
    }
}

