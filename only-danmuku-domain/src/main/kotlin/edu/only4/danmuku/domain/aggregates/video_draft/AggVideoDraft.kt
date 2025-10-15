package edu.only4.danmuku.domain.aggregates.video_draft

import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

import edu.only4.danmuku.domain.aggregates.video_draft.factory.VideoDraftFactory

/**
 * VideoDraft聚合封装
 * 视频信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class AggVideoDraft(
    payload: VideoDraftFactory.Payload? = null,
) : Aggregate.Default<VideoDraft>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) :
        com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggVideoDraft, Long>(key)

}
