package edu.only4.danmuku.domain.aggregates.video_encrypt.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.VideoHlsEncryptKey


/**
 * 单个清晰度密钥已生成事件，驱动单档位加密
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "VideoHlsEncryptKey",
    name = "VideoPostQualityKeyGeneratedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class VideoPostQualityKeyGeneratedDomainEvent(
    val entity: VideoHlsEncryptKey
)
