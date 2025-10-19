package edu.only4.danmuku.adapter.application.queries.statistics

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.draft.statistics.StatisticsSimple
import edu.only4.danmuku.application.queries._share.model.statistics.customerId
import edu.only4.danmuku.application.queries._share.model.statistics.statisticsDate
import edu.only4.danmuku.application.queries.statistics.GetPreviousDayStatisticsInfoQry
import edu.only4.danmuku.domain.aggregates.statistics.enums.StatisticsDataType
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId

/**
 * 获取前一天的统计数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetPreviousDayStatisticsInfoQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetPreviousDayStatisticsInfoQry.Request, GetPreviousDayStatisticsInfoQry.Response> {

    override fun exec(request: GetPreviousDayStatisticsInfoQry.Request): GetPreviousDayStatisticsInfoQry.Response {

        // 计算前一天零点的秒级时间戳
        val previousDayStartOfDay = LocalDate.now().minusDays(1)
            .atStartOfDay(ZoneId.systemDefault())
            .toEpochSecond()

        // 查询前一天的统计数据（支持可选 userId 过滤）
        val previousDayStatisticsList = sqlClient.findAll(StatisticsSimple::class) {
            where(table.statisticsDate eq previousDayStartOfDay)
            where(table.customerId `eq?` request.userId)
        }

        // 查询所有时间的统计数据（全量统计）
        val totalStatisticsList = sqlClient.findAll(StatisticsSimple::class) {
            where(table.customerId `eq?` request.userId)
        }

        // 按数据类型分组统计 - 前一天数据
        val previousDayCountsByType = previousDayStatisticsList
            .groupingBy { StatisticsDataType.valueOf(it.dataType.toInt()) }
            .fold(0) { acc, item -> acc + (item.statisticsCount ?: 0) }

        // 按数据类型分组统计 - 总计数据
        val totalCountsByType = totalStatisticsList
            .groupingBy { StatisticsDataType.valueOf(it.dataType.toInt()) }
            .fold(0) { acc, item -> acc + (item.statisticsCount ?: 0) }

        return GetPreviousDayStatisticsInfoQry.Response(
            // 前一天统计数据
            videoViewCount = previousDayCountsByType[StatisticsDataType.VIDEO_VIEW] ?: 0,
            videoLikeCount = previousDayCountsByType[StatisticsDataType.VIDEO_LIKE] ?: 0,
            videoCommentCount = previousDayCountsByType[StatisticsDataType.VIDEO_COMMENT] ?: 0,
            videoShareCount = previousDayCountsByType[StatisticsDataType.VIDEO_SHARE] ?: 0,
            userFollowCount = previousDayCountsByType[StatisticsDataType.USER_FOLLOW] ?: 0,
            userLoginCount = previousDayCountsByType[StatisticsDataType.USER_LOGIN] ?: 0,
            // 总计统计数据
            totalVideoViewCount = totalCountsByType[StatisticsDataType.VIDEO_VIEW] ?: 0,
            totalVideoLikeCount = totalCountsByType[StatisticsDataType.VIDEO_LIKE] ?: 0,
            totalVideoCommentCount = totalCountsByType[StatisticsDataType.VIDEO_COMMENT] ?: 0,
            totalVideoShareCount = totalCountsByType[StatisticsDataType.VIDEO_SHARE] ?: 0,
            totalUserFollowCount = totalCountsByType[StatisticsDataType.USER_FOLLOW] ?: 0,
            totalUserLoginCount = totalCountsByType[StatisticsDataType.USER_LOGIN] ?: 0
        )
    }
}
