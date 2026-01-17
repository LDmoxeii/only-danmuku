package edu.only4.danmuku.adapter.portal.api.payload.u_center_statistics

/**
 * 获取实时统计信息接口载荷
 */
object GetActualTimeStatistics {

    class Request

    data class Response(
        /**
         * 前一天数据
         * Map<统计类型, 统计数量>
         * 统计类型枚举：
         * 0: 播放 (PLAY)
         * 1: 用户数 (FANS)
         * 2: 点赞 (LIKE)
         * 3: 收藏 (COLLECTION)
         * 4: 投币 (COIN)
         * 5: 评论 (COMMENT)
         * 6: 弹幕 (DANMUKU)
         */
        var preDayData: Map<Int, Int> = emptyMap(),

        /**
         * 总统计信息
         * Map<字段名, 统计数量>
         * 包含的字段：
         * - videoViewCount: 视频观看数
         * - videoLikeCount: 视频点赞数
         * - videoCommentCount: 视频评论数
         * - videoShareCount: 视频分享数
         * - userFollowCount: 用户关注数
         * - userLoginCount: 用户登录数
         */
        var totalCountInfo: Map<String, Int> = emptyMap(),
    )
}
