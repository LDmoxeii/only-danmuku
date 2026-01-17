package edu.only4.danmuku.adapter.portal.api.admin

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.admin_index.GetActualTimeStatistics
import edu.only4.danmuku.adapter.portal.api.payload.admin_index.GetWeekStatisticsInfo
import edu.only4.danmuku.application.queries.statistics.GetPreviousDayStatisticsInfoQry
import edu.only4.danmuku.application.queries.statistics.GetTotalStatisticsInfoQry
import edu.only4.danmuku.application.queries.statistics.GetWeekStatisticsInfoQry
import edu.only4.danmuku.domain.aggregates.statistics.enums.StatisticsDataType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员统计信息控制器
 */
@RestController
@RequestMapping("/admin/index")
class AdminIndexController {

    @PostMapping("/getActualTimeStatisticsInfo")
    fun getActualTimeStatistics(): GetActualTimeStatistics.Response {
        val preDayData = Mediator.queries.send(GetPreviousDayStatisticsInfoQry.Request())
        val totalData = Mediator.queries.send(GetTotalStatisticsInfoQry.Request())

        val preDayDataMap = mapOf(
            1 to preDayData.playCount,      // 播放量 (PLAY)
            2 to preDayData.userCount,      // 用户数 (FANS)
            3 to preDayData.likeCount,      // 点赞 (LIKE)
            4 to preDayData.collectCount,   // 收藏 (COLLECTION)
            5 to preDayData.coinCount,      // 投币 (COIN)
            6 to preDayData.commentCount,   // 评论 (COMMENT)
            7 to preDayData.danmukuCount      // 弹幕 (DANMUKU)
        )

        val totalCountInfoMap = mapOf(
            "playCount" to totalData.playCount,
            "userCount" to totalData.userCount,
            "likeCount" to totalData.likeCount,
            "collectCount" to totalData.collectCount,
            "coinCount" to totalData.coinCount,
            "commentCount" to totalData.commentCount,
            "danmukuCount" to totalData.danmukuCount
        )

        return GetActualTimeStatistics.Response(
            preDayData = preDayDataMap,
            totalCountInfo = totalCountInfoMap
        )
    }

    @PostMapping("/getWeekStatisticsInfo")
    fun getWeekStatisticsInfo(@RequestBody @Validated request: GetWeekStatisticsInfo.Request): List<GetWeekStatisticsInfo.Item> {
        val weekData = Mediator.queries.send(
            GetWeekStatisticsInfoQry.Request(dataType = StatisticsDataType.valueOf(request.dataType))
        )

        return weekData.map { GetWeekStatisticsInfo.Converter.INSTANCE.fromApp(it) }
    }

}
