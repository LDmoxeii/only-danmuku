package edu.only4.danmuku.adapter.portal.api

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.UCenterGetActualTimeStatistics
import edu.only4.danmuku.adapter.portal.api.payload.UCenterGetWeekStatistics
import edu.only4.danmuku.application.queries.statistics.GetPreviousDayStatisticsInfoQry
import edu.only4.danmuku.application.queries.statistics.GetWeekStatisticsInfoQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * 用户中心-统计信息控制器
 * 处理用户数据统计信息查询
 */
@RestController
@RequestMapping("/ucenter")
@Validated
class UCenterStatisticsController {

    /**
     * 获取实时统计信息
     */
    @GetMapping("/getActualTimeStatisticsInfo")
    fun getActualTimeStatistics(): UCenterGetActualTimeStatistics.Response {
        val currentUserId = LoginHelper.getUserId()!!

        // 调用查询获取前一天统计数据和总统计数据
        val preDayData = Mediator.queries.send(
            GetPreviousDayStatisticsInfoQry.Request(
                userId = currentUserId
            )
        )

        return UCenterGetActualTimeStatistics.Response(
            preDayData = UCenterGetActualTimeStatistics.StatisticsData(
                videoPlayCount = preDayData.videoViewCount,
                videoLikeCount = preDayData.videoLikeCount,
                videoCommentCount = preDayData.videoCommentCount,
                videoShareCount = preDayData.videoShareCount,
                userFollowCount = preDayData.userFollowCount,
                userLoginCount = preDayData.userLoginCount
            ),
            totalCountInfo = UCenterGetActualTimeStatistics.StatisticsData(
                videoPlayCount = preDayData.totalVideoViewCount,
                videoLikeCount = preDayData.totalVideoLikeCount,
                videoCommentCount = preDayData.totalVideoCommentCount,
                videoShareCount = preDayData.totalVideoShareCount,
                userFollowCount = preDayData.totalUserFollowCount,
                userLoginCount = preDayData.totalUserLoginCount
            )
        )
    }

    /**
     * 获取周统计信息
     */
    @PostMapping("/getWeekStatisticsInfo")
    fun getWeekStatistics(@RequestBody request: UCenterGetWeekStatistics.Request): List<UCenterGetWeekStatistics.StatisticsItem> {
        val currentUserId = LoginHelper.getUserId()!!

        // 调用查询获取周统计数据
        val queryResult = Mediator.queries.send(
            GetWeekStatisticsInfoQry.Request(
                userId = currentUserId,
                dataType = request.dataType ?: 1
            )
        )

        // 转换为前端需要的格式 (字段名映射)
        return queryResult.map { item ->
            UCenterGetWeekStatistics.StatisticsItem(
                statisticsDate = item.date.toString(),
                statisticsCount = item.count
            )
        }
    }

}
