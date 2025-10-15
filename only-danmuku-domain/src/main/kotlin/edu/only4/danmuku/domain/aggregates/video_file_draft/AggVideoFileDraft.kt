package edu.only4.danmuku.domain.aggregates.video_file_draft

import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

import edu.only4.danmuku.domain.aggregates.video_file_draft.factory.VideoFileDraftFactory

/**
 * VideoFileDraft聚合封装
 * 视频文件信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class AggVideoFileDraft(
    payload: VideoFileDraftFactory.Payload? = null,
) : Aggregate.Default<VideoFileDraft>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) :
        com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggVideoFileDraft, Long>(key)

}
