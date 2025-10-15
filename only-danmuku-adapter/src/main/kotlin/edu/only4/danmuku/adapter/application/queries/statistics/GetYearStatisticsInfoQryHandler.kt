package edu.only4.danmuku.adapter.application.queries.statistics

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.statistics.GetYearStatisticsInfoQry

import org.springframework.stereotype.Service

/**
 * 获取年度统计数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetYearStatisticsInfoQryHandler(
) : Query<GetYearStatisticsInfoQry.Request, GetYearStatisticsInfoQry.Response> {

    override fun exec(request: GetYearStatisticsInfoQry.Request): GetYearStatisticsInfoQry.Response {

        return GetYearStatisticsInfoQry.Response(

        )
    }
}
