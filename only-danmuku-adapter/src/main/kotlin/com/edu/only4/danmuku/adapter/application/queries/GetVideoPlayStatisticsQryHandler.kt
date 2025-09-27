package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.statistics.GetVideoPlayStatisticsQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetVideoPlayStatisticsQryHandler(
) : Query<GetVideoPlayStatisticsQry.Request, GetVideoPlayStatisticsQry.Response> {

    override fun exec(request: GetVideoPlayStatisticsQry.Request): GetVideoPlayStatisticsQry.Response {

        return GetVideoPlayStatisticsQry.Response(

        )
    }
}
