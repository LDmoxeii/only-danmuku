package edu.only4.danmuku.adapter.portal.api.compatible

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.AdminIndexGetActualTimeStatistics
import edu.only4.danmuku.adapter.portal.api.payload.AdminIndexGetWeekStatistics.WeekStatisticsItem
import edu.only4.danmuku.application.queries.statistics.GetPreviousDayStatisticsInfoQry
import edu.only4.danmuku.application.queries.statistics.GetTotalStatisticsInfoQry
import edu.only4.danmuku.application.queries.statistics.GetWeekStatisticsInfoQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * 管理员统计信息控制器
 */
@RestController
@RequestMapping("/admin/index")
@Validated
class CompatibleAdminIndexController {

    @PostMapping("/getActualTimeStatisticsInfo")
    fun adminIndexGetActualTimeStatistics(): AdminIndexGetActualTimeStatistics.Response {
        val preDayData = Mediator.queries.send(GetPreviousDayStatisticsInfoQry.Request())
        val totalData = Mediator.queries.send(GetTotalStatisticsInfoQry.Request())

        // 构建前一天数据的 Map（按统计类型分类）
        val preDayDataMap = mapOf(
            1 to preDayData.playCount,      // 播放量 (PLAY)
            2 to preDayData.userCount,      // 用户数 (FANS)
            3 to preDayData.likeCount,      // 点赞 (LIKE)
            4 to preDayData.collectCount,   // 收藏 (COLLECTION)
            5 to preDayData.coinCount,      // 投币 (COIN)
            6 to preDayData.commentCount,   // 评论 (COMMENT)
            7 to preDayData.danmuCount      // 弹幕 (DANMUKU)
        )

        // 构建总统计信息的 Map（按字段名分类）
        val totalCountInfoMap = mapOf(
            "userCount" to totalData.userCount,
            "playCount" to totalData.playCount,
            "commentCount" to totalData.commentCount,
            "danmuCount" to totalData.danmuCount,
            "likeCount" to totalData.likeCount,
            "collectCount" to totalData.collectCount,
            "coinCount" to totalData.coinCount
        )

        return AdminIndexGetActualTimeStatistics.Response(
            preDayData = preDayDataMap,
            totalCountInfo = totalCountInfoMap
        )
    }

    @PostMapping("/getWeekStatisticsInfo")
    fun adminIndexGetWeekStatistics(dataType: Int): List<WeekStatisticsItem> {
        val weekData = Mediator.queries.send(
            GetWeekStatisticsInfoQry.Request(dataType = dataType)
        )

        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val resultList = weekData.map { item ->
            // 将时间戳转换为日期字符串 YYYY-MM-DD
            val dateStr = Instant.ofEpochSecond(item.date)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(dateFormatter)

            WeekStatisticsItem(
                statisticsDate = dateStr,
                statisticsCount = item.count
            )
        }

        return resultList
    }

}
