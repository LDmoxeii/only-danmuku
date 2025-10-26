package edu.only4.danmuku.adapter.application.queries.statistics

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.dataType
import edu.only4.danmuku.application.queries._share.model.dto.Statistics.StatisticsSimple
import edu.only4.danmuku.application.queries._share.model.statisticsDate
import edu.only4.danmuku.application.queries.statistics.GetWeekStatisticsInfoQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.babyfish.jimmer.sql.kt.ast.expression.ge
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId

/**
 * 获取最近七天的统计数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetWeekStatisticsInfoQryHandler(
    private val sqlClient: KSqlClient
) : ListQuery<GetWeekStatisticsInfoQry.Request, GetWeekStatisticsInfoQry.Response> {

    override fun exec(request: GetWeekStatisticsInfoQry.Request): List<GetWeekStatisticsInfoQry.Response> {

        // 计算最近7天的起始时间戳（7天前的零点）
        val sevenDaysAgoStartOfDay = LocalDate.now().minusDays(6)
            .atStartOfDay(ZoneId.systemDefault())
            .toEpochSecond()

        // 查询最近7天的统计数据，支持按 dataType 和 userId 过滤
        val statisticsList = sqlClient.findAll(StatisticsSimple::class) {
            where(table.statisticsDate ge sevenDaysAgoStartOfDay)
            where(table.dataType eq request.dataType)
            where(table.customerId `eq?` request.userId)
        }

        // 按日期分组聚合统计数量
        val groupedByDate = statisticsList
            .groupBy { it.statisticsDate }
            .mapValues { (_, items) ->
                items.sumOf { it.statisticsCount ?: 0 }
            }

        // 转换为响应格式并按日期排序
        return groupedByDate
            .map { (date, count) ->
                GetWeekStatisticsInfoQry.Response(
                    date = date,
                    count = count
                )
            }
            .sortedBy { it.date }
    }
}
