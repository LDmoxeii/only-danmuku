package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_play_history.GetPlayHistoryByVideoQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetPlayHistoryByVideoQryHandler(
) : Query<GetPlayHistoryByVideoQry.Request, GetPlayHistoryByVideoQry.Response> {

    override fun exec(request: GetPlayHistoryByVideoQry.Request): GetPlayHistoryByVideoQry.Response {

        return GetPlayHistoryByVideoQry.Response(

        )
    }
}
