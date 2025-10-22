package edu.only4.danmuku.application.queries.video_comment

import com.only4.cap4k.ddd.core.application.RequestParam

/** 判断评论是否存在 */
object CommentExistsByIdQry {
    data class Request(val commentId: Long) : RequestParam<Response>
    data class Response(val exists: Boolean)
}

