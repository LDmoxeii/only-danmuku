package edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent
import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.VideoHlsEncryptKey

/**
 * 视频 HLS 加密密钥创建事件
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "VideoHlsEncryptKey",
    name = "VideoHlsEncryptKeyCreatedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = "视频 HLS 加密密钥创建事件"
)
class VideoHlsEncryptKeyCreatedDomainEvent(val entity: VideoHlsEncryptKey)
