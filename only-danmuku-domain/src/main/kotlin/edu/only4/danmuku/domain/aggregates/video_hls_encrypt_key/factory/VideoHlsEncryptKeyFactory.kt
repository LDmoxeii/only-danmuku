package edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.VideoHlsEncryptKey
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.enums.EncryptKeyStatus
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.EncryptMethod

import org.springframework.stereotype.Service

/**
 * 视频 HLS 加密密钥
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
@Aggregate(
    aggregate = "VideoHlsEncryptKey",
    name = "VideoHlsEncryptKeyFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoHlsEncryptKeyFactory : AggregateFactory<VideoHlsEncryptKeyFactory.Payload, VideoHlsEncryptKey> {

    override fun create(payload: Payload): VideoHlsEncryptKey {
        return VideoHlsEncryptKey(
            fileId = payload.fileId,
            quality = payload.quality,
            keyId = payload.keyId,
            keyCiphertext = payload.keyCiphertext,
            ivHex = payload.ivHex,
            keyVersion = payload.keyVersion,
            method = payload.method,
            keyUriTemplate = payload.keyUriTemplate,
            expireTime = payload.expireTime,
            status = payload.status,
            remark = payload.remark,
        )
    }

     @Aggregate(
        aggregate = "VideoHlsEncryptKey",
        name = "VideoHlsEncryptKeyPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val fileId: Long,
        val quality: String?,
        val keyId: String,
        val keyCiphertext: String,
        val ivHex: String?,
        val keyVersion: Int,
        val method: EncryptMethod,
        val keyUriTemplate: String,
        val expireTime: Long?,
        val status: EncryptKeyStatus,
        val remark: String?,
    ) : AggregatePayload<VideoHlsEncryptKey>

}
