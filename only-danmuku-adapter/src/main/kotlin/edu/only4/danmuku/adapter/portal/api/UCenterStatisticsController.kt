package edu.only4.danmuku.adapter.portal.api

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

//    /**
//     * 获取实时统计信息
//     */
//    @GetMapping("/getActualTimeStatisticsInfo")
//    fun getActualTimeStatistics(): UCenterGetActualTimeStatistics.Response {
//        // TODO: 从上下文获取当前用户ID
//        val userId = 1L // 临时硬编码
//
//        // 调用查询获取前一天统计数据
//        val preDayData = Mediator.queries.send(
//            GetPreviousDayStatisticsInfoQry.Request(
//                userId = userId
//            )
//        )
//
//        // TODO: 调用查询获取总统计信息（需要补充相应的查询）
//        // 暂时返回空数据
//
//        return UCenterGetActualTimeStatistics.Response(
//            preDayData = UCenterGetActualTimeStatistics.StatisticsData(
//                videoPlayCount = preDayData.videoPlayCount,
//                videoLikeCount = preDayData.videoLikeCount,
//                videoCommentCount = preDayData.videoCommentCount,
//                videoShareCount = preDayData.videoShareCount,
//                userFollowCount = preDayData.userFollowCount,
//                userLoginCount = preDayData.userLoginCount
//            ),
//            totalCountInfo = UCenterGetActualTimeStatistics.StatisticsData(
//                videoPlayCount = preDayData.totalVideoPlayCount,
//                videoLikeCount = preDayData.totalVideoLikeCount,
//                videoCommentCount = preDayData.totalVideoCommentCount,
//                videoShareCount = preDayData.totalVideoShareCount,
//                userFollowCount = preDayData.totalUserFollowCount,
//                userLoginCount = preDayData.totalUserLoginCount
//            )
//        )
//    }
//
//    /**
//     * 获取周统计信息
//     */
//    @PostMapping("/getWeekStatisticsInfo")
//    fun getWeekStatistics(@RequestBody request: UCenterGetWeekStatistics.Request): List<UCenterGetWeekStatistics.StatisticsItem> {
//        // TODO: 从上下文获取当前用户ID
//        val userId = 1L // 临时硬编码
//
//        // 调用查询获取周统计数据
//        val queryResult = Mediator.queries.send(
//            GetWeekStatisticsInfoQry.Request(
//                userId = userId,
//                dataType = request.dataType
//            )
//        )
//
//        // 转换为前端需要的格式
//        return queryResult.list.map { item ->
//            UCenterGetWeekStatistics.StatisticsItem(
//                statisticsDate = item.statisticsDate,
//                statisticsCount = item.statisticsCount
//            )
//        }
//    }

}
