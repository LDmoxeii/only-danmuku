package edu.only4.danmuku.application.queries.video_comment

import com.only4.cap4k.ddd.core.application.query.PageQueryParam

/**
 * 评论分页
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object VideoCommentPageQry {

    data class Request(
        var videoId: Long? = null,
        var videoUserId: Long? = null,
        var videoNameFuzzy: String? = null,
    ) : PageQueryParam<Response>()

    data class Response(
        val commentId: Long,
        val parentCommentId: Long,
        val videoId: Long,
        val videoUserId: Long,
        val videoName: String,
        val videoCover: String,
        val content: String? = null,
        val imgPath: String? = null,
        val customerId: Long,
        val customerNickname: String,
        val customerAvatar: String? = null,
        val replyCustomerId: Long? = null,
        val replyCustomerNickname: String? = null,
        val postTime: Long,
        val likeCount: Int? = 0,
        val hateCount: Int? = 0,
        val topType: Int? = 0,
        val childrenCount: Int = 0,
        val children: List<Response>? = null,
    )
}
