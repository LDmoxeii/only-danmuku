package edu.only4.danmuku.adapter.application.queries.statistics

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.Statistics
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.dataType
import edu.only4.danmuku.application.queries._share.model.statisticsCount
import edu.only4.danmuku.application.queries.statistics.GetTotalStatisticsInfoQry
import edu.only4.danmuku.domain.aggregates.statistics.enums.StatisticsDataType
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
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

        val statisticsList = sqlClient.createQuery(Statistics::class) {
            where(table.customerId `eq?` request.userId)
            select(table.dataType, table.statisticsCount)
        }.execute()

        // 按数据类型分组统计（合并）
        val countsByType = statisticsList
            .groupingBy { it._1 }
            .fold(0) { acc, item -> acc + (item._2 ?: 0) }

        return GetTotalStatisticsInfoQry.Response(
            userCount = countsByType[StatisticsDataType.FANS] ?: 0,        // 粉丝
            playCount = countsByType[StatisticsDataType.PLAY] ?: 0,        // 播放量
            commentCount = countsByType[StatisticsDataType.COMMENT] ?: 0,  // 评论
            danmukuCount = countsByType[StatisticsDataType.DANMUKU] ?: 0,      // 弹幕
            likeCount = countsByType[StatisticsDataType.LIKE] ?: 0,        // 点赞
            collectCount = countsByType[StatisticsDataType.COLLECTION] ?: 0, // 收藏
            coinCount = countsByType[StatisticsDataType.COIN] ?: 0         // 投币
        )
    }
}
