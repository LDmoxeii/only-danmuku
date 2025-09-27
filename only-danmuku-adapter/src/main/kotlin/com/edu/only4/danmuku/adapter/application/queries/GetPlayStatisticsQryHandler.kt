package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_play_history.GetPlayStatisticsQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetPlayStatisticsQryHandler(
) : Query<GetPlayStatisticsQry.Request, GetPlayStatisticsQry.Response> {

    override fun exec(request: GetPlayStatisticsQry.Request): GetPlayStatisticsQry.Response {

        return GetPlayStatisticsQry.Response(

        )
    }
}
