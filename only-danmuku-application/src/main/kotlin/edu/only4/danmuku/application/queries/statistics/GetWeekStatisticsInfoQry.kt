package edu.only4.danmuku.application.queries.statistics

import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 获取最近七天的统计数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetWeekStatisticsInfoQry {

    data class Request(
        /** 数据类型：1-视频观看，2-视频点赞，3-视频评论，4-视频分享，5-用户关注，6-用户登录 */
        val dataType: Int?
    ) : ListQueryParam<Response>

    data class Response(
        /** 日期（时间戳） */
        val date: Long,
        /** 统计数量 */
        val count: Int
    )
}
