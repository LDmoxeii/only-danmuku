package edu.only4.danmuku.application.queries.statistics

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取前一天的统计数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetPreviousDayStatisticsInfoQry {

    data class Request(
        /** 用户ID - 可选，不传则查询全局统计 */
        val userId: Long? = null,
    ) : RequestParam<Response>

    data class Response(
        /** 前一天视频观看数 */
        val videoViewCount: Int = 0,
        /** 前一天视频点赞数 */
        val videoLikeCount: Int = 0,
        /** 前一天视频评论数 */
        val videoCommentCount: Int = 0,
        /** 前一天视频分享数 */
        val videoShareCount: Int = 0,
        /** 前一天用户关注数 */
        val userFollowCount: Int = 0,
        /** 前一天用户登录数 */
        val userLoginCount: Int = 0,
        /** 总视频观看数 */
        val totalVideoViewCount: Int = 0,
        /** 总视频点赞数 */
        val totalVideoLikeCount: Int = 0,
        /** 总视频评论数 */
        val totalVideoCommentCount: Int = 0,
        /** 总视频分享数 */
        val totalVideoShareCount: Int = 0,
        /** 总用户关注数 */
        val totalUserFollowCount: Int = 0,
        /** 总用户登录数 */
        val totalUserLoginCount: Int = 0,
    )
}
