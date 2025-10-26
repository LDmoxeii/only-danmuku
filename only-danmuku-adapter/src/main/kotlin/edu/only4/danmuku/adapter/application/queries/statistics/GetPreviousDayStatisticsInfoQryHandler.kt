package edu.only4.danmuku.adapter.application.queries.statistics

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.dto.Statistics.StatisticsSimple
import edu.only4.danmuku.application.queries._share.model.statisticsDate
import edu.only4.danmuku.application.queries.statistics.GetPreviousDayStatisticsInfoQry
import edu.only4.danmuku.domain.aggregates.statistics.enums.StatisticsDataType
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.babyfish.jimmer.sql.kt.ast.expression.ge
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
            where(table.statisticsDate ge previousDayStartOfDay)
            where(table.customerId `eq?` request.userId)
        }

        // 按数据类型分组统计 - 前一天数据
        val previousDayCountsByType = previousDayStatisticsList
            .groupingBy { StatisticsDataType.valueOf(it.dataType) }
            .fold(0) { acc, item -> acc + (item.statisticsCount ?: 0) }

        return GetPreviousDayStatisticsInfoQry.Response(
            userCount = previousDayCountsByType[StatisticsDataType.FANS] ?: 0,        // 粉丝
            playCount = previousDayCountsByType[StatisticsDataType.PLAY] ?: 0,        // 播放量
            commentCount = previousDayCountsByType[StatisticsDataType.COMMENT] ?: 0,  // 评论
            danmuCount = previousDayCountsByType[StatisticsDataType.DANMU] ?: 0,      // 弹幕
            likeCount = previousDayCountsByType[StatisticsDataType.LIKE] ?: 0,        // 点赞
            collectCount = previousDayCountsByType[StatisticsDataType.COLLECTION] ?: 0, // 收藏
            coinCount = previousDayCountsByType[StatisticsDataType.COIN] ?: 0         // 投币
        )
    }
}
