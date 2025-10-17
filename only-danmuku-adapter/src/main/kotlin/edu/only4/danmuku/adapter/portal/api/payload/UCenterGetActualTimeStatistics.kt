package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 获取实时统计信息接口载荷
 */
object UCenterGetActualTimeStatistics {

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
        /** 视频播放数 */
        var videoPlayCount: Int? = null,
        /** 视频点赞数 */
        var videoLikeCount: Int? = null,
        /** 视频评论数 */
        var videoCommentCount: Int? = null,
        /** 视频分享数 */
        var videoShareCount: Int? = null,
        /** 用户关注数 */
        var userFollowCount: Int? = null,
        /** 用户登录数 */
        var userLoginCount: Int? = null
    )
}
