package edu.only4.danmuku.adapter.application.queries.statistics

import com.only4.cap4k.ddd.core.application.query.ListQuery
import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.statistics.GetWeekStatisticsInfoQry

import org.springframework.stereotype.Service

/**
 * 获取最近七天的统计数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetWeekStatisticsInfoQryHandler(
) : ListQuery<GetWeekStatisticsInfoQry.Request, GetWeekStatisticsInfoQry.Response> {

    override fun exec(request: GetWeekStatisticsInfoQry.Request): List<GetWeekStatisticsInfoQry.Response> {

        return listOf(
//            GetWeekStatisticsInfoQry.Response(
//
//        )
        )
    }
}
