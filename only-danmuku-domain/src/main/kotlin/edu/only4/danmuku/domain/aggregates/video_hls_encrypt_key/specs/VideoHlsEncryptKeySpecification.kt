package edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.specs

import com.only4.cap4k.ddd.core.domain.aggregate.Specification
import com.only4.cap4k.ddd.core.domain.aggregate.Specification.Result
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_hls_encrypt_key.VideoHlsEncryptKey

import org.springframework.stereotype.Service

/**
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义规约方法
 * @author cap4k-ddd-codegen
 */
@Service
@Aggregate(
    aggregate = "VideoHlsEncryptKey",
    name = "VideoHlsEncryptKeySpecification",
    type = Aggregate.TYPE_SPECIFICATION,
    description = ""
)
class VideoHlsEncryptKeySpecification : Specification<VideoHlsEncryptKey> {

    override fun specify(entity: VideoHlsEncryptKey): Result {
        return Result.pass()
    }

}
