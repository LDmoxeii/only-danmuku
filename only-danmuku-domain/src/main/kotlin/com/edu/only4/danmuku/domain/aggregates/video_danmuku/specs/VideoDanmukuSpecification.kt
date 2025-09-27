package com.edu.only4.danmuku.domain.aggregates.video_danmuku.specs

import com.edu.only4.danmuku.domain.aggregates.video_danmuku.VideoDanmuku
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
@Aggregate(aggregate = "VideoDanmuku", name = "VideoDanmukuSpecification", type = Aggregate.TYPE_SPECIFICATION, description = "")
class VideoDanmukuSpecification : Specification<VideoDanmuku> {

    override fun specify(entity: VideoDanmuku): Result {
        return Result.pass()
    }

}
