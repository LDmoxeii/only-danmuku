package edu.only4.danmuku.adapter.portal.api

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员统计信息控制器
 */
@RestController
@RequestMapping("/admin/index/v2")
@Validated
class AdminIndexController {

//    @PostMapping("/getActualTimeStatisticsInfo")
//    fun adminIndexGetActualTimeStatistics(): AdminIndexGetActualTimeStatistics.Response {
//        val preDayData = Mediator.queries.send(GetPreviousDayStatisticsInfoQry.Request())
//        val totalData = Mediator.queries.send(GetTotalStatisticsInfoQry.Request())
//
//        val preDayDataObj = AdminIndexGetActualTimeStatistics.StatisticsData(
//            videoViewCount = preDayData.videoViewCount,
//            videoLikeCount = preDayData.videoLikeCount,
//            videoCommentCount = preDayData.videoCommentCount,
//            videoShareCount = preDayData.videoShareCount,
//            userFollowCount = preDayData.userFollowCount,
//            userLoginCount = preDayData.userLoginCount
//        )
//
//        val totalCountInfoObj = AdminIndexGetActualTimeStatistics.StatisticsData(
//            videoViewCount = totalData.videoViewCount,
//            videoLikeCount = totalData.videoLikeCount,
//            videoCommentCount = totalData.videoCommentCount,
//            videoShareCount = totalData.videoShareCount,
//            userFollowCount = totalData.userFollowCount,
//            userLoginCount = totalData.userLoginCount
//        )
//
//        return AdminIndexGetActualTimeStatistics.Response(
//            preDayData = preDayDataObj,
//            totalCountInfo = totalCountInfoObj
//        )
//    }
//
//    @PostMapping("/getWeekStatisticsInfo")
//    fun adminIndexGetWeekStatistics(@RequestBody request: AdminIndexGetWeekStatistics.Request): AdminIndexGetWeekStatistics.Response {
//        val weekData = Mediator.queries.send(
//            GetWeekStatisticsInfoQry.Request(dataType = request.dataType)
//        )
//
//        val resultList = weekData.map { item ->
//            AdminIndexGetWeekStatistics.WeekStatisticsItem(
//                statisticsDate = item.date,
//                statisticsCount = item.count
//            )
//        }
//
//        return AdminIndexGetWeekStatistics.Response(list = resultList)
//    }

}
