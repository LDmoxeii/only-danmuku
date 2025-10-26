package edu.only4.danmuku.domain.aggregates.video_danmuku.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.video_danmuku.VideoDanmuku


/**
 * 弹幕已批量删除
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "VideoDanmuku",
    name = "DanmukuBatchDeletedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class DanmukuBatchDeletedDomainEvent(val entity: VideoDanmuku)
