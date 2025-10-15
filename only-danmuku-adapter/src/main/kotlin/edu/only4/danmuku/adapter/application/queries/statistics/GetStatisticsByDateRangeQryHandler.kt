package edu.only4.danmuku.adapter.application.queries.statistics

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.statistics.GetStatisticsByDateRangeQry

import org.springframework.stereotype.Service

/**
 * 按日期范围获取统计数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetStatisticsByDateRangeQryHandler(
) : Query<GetStatisticsByDateRangeQry.Request, GetStatisticsByDateRangeQry.Response> {

    override fun exec(request: GetStatisticsByDateRangeQry.Request): GetStatisticsByDateRangeQry.Response {

        return GetStatisticsByDateRangeQry.Response(

        )
    }
}
