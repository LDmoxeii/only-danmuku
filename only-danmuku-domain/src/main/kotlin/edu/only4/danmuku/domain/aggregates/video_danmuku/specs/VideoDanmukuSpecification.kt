package edu.only4.danmuku.domain.aggregates.video_danmuku

import com.only4.cap4k.ddd.core.domain.aggregate.Specification
import com.only4.cap4k.ddd.core.domain.aggregate.Specification.Result
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import org.springframework.stereotype.Service

/**
 * 视频弹幕;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义规约方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "VideoDanmuku",
    name = "VideoDanmukuSpecification",
    type = Aggregate.TYPE_SPECIFICATION,
    description = ""
)
class VideoDanmukuSpecification : Specification<VideoDanmuku> {

    override fun specify(entity: VideoDanmuku): Result {
        return Result.pass()
    }

}
