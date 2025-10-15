package edu.only4.danmuku.domain.aggregates.video_danmuku

import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

import edu.only4.danmuku.domain.aggregates.video_danmuku.factory.VideoDanmukuFactory

/**
 * VideoDanmuku聚合封装
 * 视频弹幕;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class AggVideoDanmuku(
    payload: VideoDanmukuFactory.Payload? = null,
) : Aggregate.Default<VideoDanmuku>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) :
        com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggVideoDanmuku, Long>(key)

}
