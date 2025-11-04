package edu.only4.danmuku.adapter.portal.api.compatible

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.UCenterGetActualTimeStatistics
import edu.only4.danmuku.adapter.portal.api.payload.UCenterGetWeekStatistics
import edu.only4.danmuku.adapter.portal.api.payload.UCenterGetWeekStatistics.Converter
import edu.only4.danmuku.application.queries.statistics.GetPreviousDayStatisticsInfoQry
import edu.only4.danmuku.application.queries.statistics.GetTotalStatisticsInfoQry
import edu.only4.danmuku.application.queries.statistics.GetWeekStatisticsInfoQry
import edu.only4.danmuku.domain.aggregates.statistics.enums.StatisticsDataType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ucenter")
@Validated
class CompatibleUCenterStatisticsController {

    @PostMapping("/getActualTimeStatisticsInfo")
    fun getActualTimeStatistics(): UCenterGetActualTimeStatistics.Response {
        val currentUserId = LoginHelper.getUserId()!!
        val preDayData = Mediator.queries.send(GetPreviousDayStatisticsInfoQry.Request(currentUserId))
        val totalData = Mediator.queries.send(GetTotalStatisticsInfoQry.Request(currentUserId))

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
            "danmuCount" to totalData.danmukuCount
        )

        return UCenterGetActualTimeStatistics.Response(
            preDayData = preDayDataMap,
            totalCountInfo = totalCountInfoMap
        )
    }

    @PostMapping("/getWeekStatisticsInfo")
    fun getWeekStatistics(dataType: Int): List<UCenterGetWeekStatistics.WeekStatisticsItem> {
        val currentUserId = LoginHelper.getUserId()!!

        val weekData = Mediator.queries.send(
            GetWeekStatisticsInfoQry.Request(
                dataType = StatisticsDataType.valueOf(dataType),
                userId = currentUserId
            )
        )

        return weekData.map { Converter.INSTANCE.fromApp(it) }
    }

}
