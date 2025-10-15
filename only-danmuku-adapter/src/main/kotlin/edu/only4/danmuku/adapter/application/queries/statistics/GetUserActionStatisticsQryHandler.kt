package edu.only4.danmuku.adapter.application.queries.statistics

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.statistics.GetUserActionStatisticsQry

import org.springframework.stereotype.Service

/**
 * 获取用户行为统计
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetUserActionStatisticsQryHandler(
) : Query<GetUserActionStatisticsQry.Request, GetUserActionStatisticsQry.Response> {

    override fun exec(request: GetUserActionStatisticsQry.Request): GetUserActionStatisticsQry.Response {

        return GetUserActionStatisticsQry.Response(

        )
    }
}
