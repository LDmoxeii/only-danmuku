package edu.only4.danmuku.adapter.application.queries.statistics

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.statistics.GetMonthStatisticsInfoQry

import org.springframework.stereotype.Service

/**
 * 获取月度统计数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetMonthStatisticsInfoQryHandler(
) : Query<GetMonthStatisticsInfoQry.Request, GetMonthStatisticsInfoQry.Response> {

    override fun exec(request: GetMonthStatisticsInfoQry.Request): GetMonthStatisticsInfoQry.Response {

        return GetMonthStatisticsInfoQry.Response(

        )
    }
}
