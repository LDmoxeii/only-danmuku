package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 获取实时统计信息接口载荷
 */
object AdminIndexGetActualTimeStatistics {

    class Request

    data class Response(
        /** 前一天数据 */
        var preDayData: StatisticsData? = null,
        /** 总统计信息 */
        var totalCountInfo: StatisticsData? = null
    )

    /**
     * 统计数据
     */
    data class StatisticsData(
        /** 视频观看数 */
        var videoViewCount: Int = 0,
        /** 视频点赞数 */
        var videoLikeCount: Int = 0,
        /** 视频评论数 */
        var videoCommentCount: Int = 0,
        /** 视频分享数 */
        var videoShareCount: Int = 0,
        /** 用户关注数 */
        var userFollowCount: Int = 0,
        /** 用户登录数 */
        var userLoginCount: Int = 0
    )
}
