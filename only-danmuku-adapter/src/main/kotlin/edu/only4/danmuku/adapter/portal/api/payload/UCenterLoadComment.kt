package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam

/**
 * 加载用户收到的评论接口载荷
 */
object UCenterLoadComment {

    data class Request(
        /** 视频ID */
        val videoId: String? = null
    ) : PageParam()

    /**
     * 评论项
     */
    data class CommentItem(
        /** 评论ID */
        var commentId: String? = null,
        /** 视频ID */
        var videoId: String? = null,
        /** 视频名称 */
        var videoName: String? = null,
        /** 评论内容 */
        var content: String? = null,
        /** 用户ID */
        var userId: String? = null,
        /** 用户昵称 */
        var nickName: String? = null,
        /** 发布时间 */
        var postTime: String? = null
    )
}
