package com.edu.only4.danmuku.domain.aggregates.video_draft.specs

import com.edu.only4.danmuku.domain.aggregates.video_draft.VideoDraft
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
@Aggregate(aggregate = "VideoDraft", name = "VideoDraftSpecification", type = Aggregate.TYPE_SPECIFICATION, description = "")
class VideoDraftSpecification : Specification<VideoDraft> {

    override fun specify(entity: VideoDraft): Result {
        return Result.pass()
    }

}
