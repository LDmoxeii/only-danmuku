package edu.only4.danmuku.adapter.portal.api

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.AdminIndexGetActualTimeStatistics
import edu.only4.danmuku.adapter.portal.api.payload.AdminIndexGetWeekStatistics
import edu.only4.danmuku.application.queries.statistics.GetPreviousDayStatisticsInfoQry
import edu.only4.danmuku.application.queries.statistics.GetTotalStatisticsInfoQry
import edu.only4.danmuku.application.queries.statistics.GetWeekStatisticsInfoQry
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
@Validated
class AdminIndexController {

    /**
     * 获取实时统计信息
     */
    @PostMapping("/getActualTimeStatisticsInfo")
    fun adminIndexGetActualTimeStatistics(): AdminIndexGetActualTimeStatistics.Response {
        // 获取前一天的统计数据
        val preDayData = Mediator.queries.send(GetPreviousDayStatisticsInfoQry.Request())
        val totalData = Mediator.queries.send(GetTotalStatisticsInfoQry.Request())

        // 转换为 StatisticsData 对象
        val preDayDataObj = AdminIndexGetActualTimeStatistics.StatisticsData(
            videoViewCount = preDayData.videoViewCount,
            videoLikeCount = preDayData.videoLikeCount,
            videoCommentCount = preDayData.videoCommentCount,
            videoShareCount = preDayData.videoShareCount,
            userFollowCount = preDayData.userFollowCount,
            userLoginCount = preDayData.userLoginCount
        )

        val totalCountInfoObj = AdminIndexGetActualTimeStatistics.StatisticsData(
            videoViewCount = totalData.videoViewCount,
            videoLikeCount = totalData.videoLikeCount,
            videoCommentCount = totalData.videoCommentCount,
            videoShareCount = totalData.videoShareCount,
            userFollowCount = totalData.userFollowCount,
            userLoginCount = totalData.userLoginCount
        )

        return AdminIndexGetActualTimeStatistics.Response(
            preDayData = preDayDataObj,
            totalCountInfo = totalCountInfoObj
        )
    }

    /**
     * 获取周统计信息
     */
    @PostMapping("/getWeekStatisticsInfo")
    fun adminIndexGetWeekStatistics(@RequestBody request: AdminIndexGetWeekStatistics.Request): AdminIndexGetWeekStatistics.Response {
        // 获取最近7天的统计数据
        val weekData = Mediator.queries.send(
            GetWeekStatisticsInfoQry.Request(dataType = request.dataType)
        )

        // 转换为 WeekStatisticsItem 对象列表
        val resultList = weekData.map { item ->
            AdminIndexGetWeekStatistics.WeekStatisticsItem(
                statisticsDate = item.date,
                statisticsCount = item.count
            )
        }

        return AdminIndexGetWeekStatistics.Response(list = resultList)
    }

}
