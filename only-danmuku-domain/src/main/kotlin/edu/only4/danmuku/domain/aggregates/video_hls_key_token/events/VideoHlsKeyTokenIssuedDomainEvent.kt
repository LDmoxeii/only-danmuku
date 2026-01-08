package edu.only4.danmuku.domain.aggregates.video_hls_key_token.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.video_hls_key_token.VideoHlsKeyToken


/**
 * 视频 HLS 密钥 Token 已签发
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/08
 */
@DomainEvent(persist = true)
@Aggregate(
    aggregate = "VideoHlsKeyToken",
    name = "VideoHlsKeyTokenIssuedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class VideoHlsKeyTokenIssuedDomainEvent(
    val entity: VideoHlsKeyToken
)
