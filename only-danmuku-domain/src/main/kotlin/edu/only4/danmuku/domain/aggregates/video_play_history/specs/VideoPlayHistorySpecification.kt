package edu.only4.danmuku.domain.aggregates.video_play_history

import com.only4.cap4k.ddd.core.domain.aggregate.Specification
import com.only4.cap4k.ddd.core.domain.aggregate.Specification.Result
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import org.springframework.stereotype.Service

/**
 * 视频播放历史;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义规约方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "VideoPlayHistory",
    name = "VideoPlayHistorySpecification",
    type = Aggregate.TYPE_SPECIFICATION,
    description = ""
)
class VideoPlayHistorySpecification : Specification<VideoPlayHistory> {

    override fun specify(entity: VideoPlayHistory): Result {
        return Result.pass()
    }

}
