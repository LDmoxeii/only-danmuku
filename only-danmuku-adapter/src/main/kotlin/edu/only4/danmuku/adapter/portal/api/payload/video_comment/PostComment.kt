package edu.only4.danmuku.adapter.portal.api.payload.video_comment

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

object PostComment {

    data class Request(
        val videoId: Long,
        val replyCommentId: Long?,
        @param:NotEmpty @param:Size(max = 500) val content: String,
        @param:Size(max = 50) val imgPath: String?,
    )
}
