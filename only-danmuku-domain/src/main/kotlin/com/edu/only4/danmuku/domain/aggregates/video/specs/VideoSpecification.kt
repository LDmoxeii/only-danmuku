package com.edu.only4.danmuku.domain.aggregates.video.specs

import com.edu.only4.danmuku.domain.aggregates.video.Video
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
@Aggregate(aggregate = "Video", name = "VideoSpecification", type = Aggregate.TYPE_SPECIFICATION, description = "")
class VideoSpecification : Specification<Video> {

    override fun specify(entity: Video): Result {
        return Result.pass()
    }

}
