package edu.only4.danmuku.domain.aggregates.video_play_history

import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

import edu.only4.danmuku.domain.aggregates.video_play_history.factory.VideoPlayHistoryFactory

/**
 * VideoPlayHistory聚合封装
 * 视频播放历史;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class AggVideoPlayHistory(
    payload: VideoPlayHistoryFactory.Payload? = null,
) : Aggregate.Default<VideoPlayHistory>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) :
        com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggVideoPlayHistory, Long>(key)

}
