package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_play_history.GetPlayHistoryCountQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetPlayHistoryCountQryHandler(
) : Query<GetPlayHistoryCountQry.Request, GetPlayHistoryCountQry.Response> {

    override fun exec(request: GetPlayHistoryCountQry.Request): GetPlayHistoryCountQry.Response {

        return GetPlayHistoryCountQry.Response(

        )
    }
}
