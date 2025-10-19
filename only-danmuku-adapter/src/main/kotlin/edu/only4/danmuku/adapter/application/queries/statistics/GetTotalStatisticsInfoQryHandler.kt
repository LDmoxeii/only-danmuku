package edu.only4.danmuku.adapter.application.queries.statistics

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.draft.statistics.PreviousDayStatistics
import edu.only4.danmuku.application.queries.statistics.GetTotalStatisticsInfoQry
import edu.only4.danmuku.domain.aggregates.statistics.enums.StatisticsDataType
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.stereotype.Service

/**
 * 获取总统计数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/18
 */
@Service
class GetTotalStatisticsInfoQryHandler(
    private val sqlClient: KSqlClient
) : Query<GetTotalStatisticsInfoQry.Request, GetTotalStatisticsInfoQry.Response> {

    override fun exec(request: GetTotalStatisticsInfoQry.Request): GetTotalStatisticsInfoQry.Response {

        // 查询所有统计数据（不限日期）
        val statisticsList = sqlClient.findAll(PreviousDayStatistics::class)

        // 按数据类型分组统计（合并）
        val countsByType = statisticsList
            .groupingBy { StatisticsDataType.valueOf(it.dataType.toInt()) }
            .fold(0) { acc, item -> acc + (item.statisticsCount ?: 0) }

        val videoViewCount = countsByType[StatisticsDataType.VIDEO_VIEW] ?: 0
        val videoLikeCount = countsByType[StatisticsDataType.VIDEO_LIKE] ?: 0
        val videoCommentCount = countsByType[StatisticsDataType.VIDEO_COMMENT] ?: 0
        val videoShareCount = countsByType[StatisticsDataType.VIDEO_SHARE] ?: 0
        val userFollowCount = countsByType[StatisticsDataType.USER_FOLLOW] ?: 0
        val userLoginCount = countsByType[StatisticsDataType.USER_LOGIN] ?: 0

        return GetTotalStatisticsInfoQry.Response(
            videoViewCount = videoViewCount,
            videoLikeCount = videoLikeCount,
            videoCommentCount = videoCommentCount,
            videoShareCount = videoShareCount,
            userFollowCount = userFollowCount,
            userLoginCount = userLoginCount
        )
    }
}
