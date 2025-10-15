package edu.only4.danmuku.domain.aggregates.video_file

import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

import edu.only4.danmuku.domain.aggregates.video_file.factory.VideoFileFactory

/**
 * VideoFile聚合封装
 * 视频文件信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
class AggVideoFile(
    payload: VideoFileFactory.Payload? = null,
) : Aggregate.Default<VideoFile>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) :
        com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggVideoFile, Long>(key)

}
