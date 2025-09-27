package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_play_history.GetUserPlayHistoryQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetUserPlayHistoryQryHandler(
) : Query<GetUserPlayHistoryQry.Request, GetUserPlayHistoryQry.Response> {

    override fun exec(request: GetUserPlayHistoryQry.Request): GetUserPlayHistoryQry.Response {

        return GetUserPlayHistoryQry.Response(

        )
    }
}
