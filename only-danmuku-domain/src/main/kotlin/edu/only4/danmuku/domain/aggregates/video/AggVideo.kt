package edu.only4.danmuku.domain.aggregates.video

import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

import edu.only4.danmuku.domain.aggregates.video.factory.VideoFactory

/**
 * Video聚合封装
 * 视频信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class AggVideo(
    payload: VideoFactory.Payload? = null,
) : Aggregate.Default<Video>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) :
        com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggVideo, Long>(key)

}
