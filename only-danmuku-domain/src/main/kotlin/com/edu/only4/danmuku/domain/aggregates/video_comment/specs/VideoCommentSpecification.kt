package com.edu.only4.danmuku.domain.aggregates.video_comment.specs

import com.edu.only4.danmuku.domain.aggregates.video_comment.VideoComment
import com.only4.cap4k.ddd.core.domain.aggregate.Specification
import com.only4.cap4k.ddd.core.domain.aggregate.Specification.Result
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

/**
 * 
 *
 * @author cap4k-ddd-codegen
 * @date 2025/09/27
 */
@Service
@Aggregate(aggregate = "VideoComment", name = "VideoCommentSpecification", type = Aggregate.TYPE_SPECIFICATION, description = "")
class VideoCommentSpecification : Specification<VideoComment> {

    override fun specify(entity: VideoComment): Result {
        return Result.pass()
    }

}
