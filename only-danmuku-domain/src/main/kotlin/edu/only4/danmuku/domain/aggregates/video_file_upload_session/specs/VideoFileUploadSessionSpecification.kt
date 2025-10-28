package edu.only4.danmuku.domain.aggregates.video_file_upload_session

import com.only4.cap4k.ddd.core.domain.aggregate.Specification
import com.only4.cap4k.ddd.core.domain.aggregate.Specification.Result
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import org.springframework.stereotype.Service

/**
 * 视频分片上传会话; 用于跟踪预上传与分片进度
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义规约方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/28
 */
@Service
@Aggregate(
    aggregate = "VideoFileUploadSession",
    name = "VideoFileUploadSessionSpecification",
    type = Aggregate.TYPE_SPECIFICATION,
    description = ""
)
class VideoFileUploadSessionSpecification : Specification<VideoFileUploadSession> {

    override fun specify(entity: VideoFileUploadSession): Result {
        return Result.pass()
    }

}
