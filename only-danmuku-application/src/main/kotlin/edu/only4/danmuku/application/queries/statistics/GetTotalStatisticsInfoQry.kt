package edu.only4.danmuku.application.queries.statistics

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取总统计数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/18
 */
object GetTotalStatisticsInfoQry {

    class Request: RequestParam<Response>

    data class Response(
        /** 视频观看数 */
        val videoViewCount: Int = 0,
        /** 视频点赞数 */
        val videoLikeCount: Int = 0,
        /** 视频评论数 */
        val videoCommentCount: Int = 0,
        /** 视频分享数 */
        val videoShareCount: Int = 0,
        /** 用户关注数 */
        val userFollowCount: Int = 0,
        /** 用户登录数 */
        val userLoginCount: Int = 0,
        )
}
