package edu.only4.danmuku.domain.aggregates.video_hls_key_token.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_hls_key_token.VideoHlsKeyToken
import edu.only4.danmuku.domain.aggregates.video_hls_key_token.enums.EncryptTokenStatus

import org.springframework.stereotype.Service

/**
 * HLS 加密播放 token
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/26
 */
@Service
@Aggregate(
    aggregate = "VideoHlsKeyToken",
    name = "VideoHlsKeyTokenFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoHlsKeyTokenFactory : AggregateFactory<VideoHlsKeyTokenFactory.Payload, VideoHlsKeyToken> {

    override fun create(payload: Payload): VideoHlsKeyToken {
        return VideoHlsKeyToken(
            videoPostId = payload.videoPostId,
            videoId = payload.videoId,
            fileIndex = payload.fileIndex,
            keyVersion = payload.keyVersion,
            allowedQualities = payload.allowedQualities,
            tokenHash = payload.tokenHash,
            audience = payload.audience,
            expireTime = payload.expireTime,
            maxUse = payload.maxUse.coerceAtLeast(1),
            usedCount = 0,
            status = EncryptTokenStatus.VALID
        )
    }

     @Aggregate(
        aggregate = "VideoHlsKeyToken",
        name = "VideoHlsKeyTokenPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
         val videoPostId: Long,
         val videoId: Long,
         val fileIndex: Int,
         val keyVersion: Int,
         val allowedQualities: String?,
         val tokenHash: String,
         val audience: String?,
         val expireTime: Long,
         val maxUse: Int,
    ) : AggregatePayload<VideoHlsKeyToken>

}
