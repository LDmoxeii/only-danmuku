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
        /** 视频名称模糊查询 */
        val videoNameFuzzy: String? = null
    ) : PageQueryParam<Response>()

    data class Response(
        /** 评论ID */
        val commentId: Long,
        /** 父评论ID */
        val parentCommentId: Long,
        /** 视频ID */
        val videoId: Long,
        /** 视频名称 */
        val videoName: String,
        /** 视频封面 */
        val videoCover: String,
        /** 评论内容 */
        val content: String? = null,
        /** 评论图片路径 */
        val imgPath: String? = null,
        /** 用户ID */
        val customerId: Long,
        /** 用户昵称 */
        val customerNickname: String,
        /** 用户头像 */
        val customerAvatar: String? = null,
        /** 回复用户ID */
        val replyCustomerId: Long? = null,
        /** 回复用户昵称 */
        val replyCustomerNickname: String? = null,
        /** 发布时间 */
        val postTime: Long,
        /** 点赞数 */
        val likeCount: Int? = 0,
        /** 讨厌数 */
        val hateCount: Int? = 0,
        /** 是否置顶 */
        val topType: Byte? = 0,
    )
}
