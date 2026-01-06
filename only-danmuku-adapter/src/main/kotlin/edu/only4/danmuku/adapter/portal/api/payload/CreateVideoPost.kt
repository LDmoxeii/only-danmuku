package edu.only4.danmuku.adapter.portal.api.payload

import com.only.engine.json.validate.JsonPattern
import com.only.engine.json.validate.JsonType
import edu.only4.danmuku.domain.aggregates.video_post.enums.PostType
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

/**
 * 创建视频投稿
 */
object CreateVideoPost {

    data class Request(
        @param:NotEmpty val videoCover: String,
        @param:NotEmpty @param:Size(max = 100) val videoName: String,
        val parentCategoryId: Long,
        val categoryId: Long?,
        val postType: PostType,
        val originInfo: String?,
        @param:NotEmpty @param:Size(max = 300) val tags: String,
        @param:Size(max = 2000) val introduction: String?,
        @param:Size(max = 3) val interaction: String?,
        @param:NotEmpty @param:JsonPattern(type = JsonType.ARRAY) val uploadFileList: String,
    )

    class Response()

    data class PostFileItem(
        val uploadId: Long,
        val fileName: String,
        val fileIndex: Int? = null,
        val fileSize: Long? = null,
        val duration: Int? = null,
    )
}
