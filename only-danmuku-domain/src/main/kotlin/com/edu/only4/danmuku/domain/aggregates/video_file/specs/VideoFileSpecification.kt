package com.edu.only4.danmuku.domain.aggregates.video_file.specs

import com.edu.only4.danmuku.domain.aggregates.video_file.VideoFile
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
@Aggregate(aggregate = "VideoFile", name = "VideoFileSpecification", type = Aggregate.TYPE_SPECIFICATION, description = "")
class VideoFileSpecification : Specification<VideoFile> {

    override fun specify(entity: VideoFile): Result {
        return Result.pass()
    }

}
