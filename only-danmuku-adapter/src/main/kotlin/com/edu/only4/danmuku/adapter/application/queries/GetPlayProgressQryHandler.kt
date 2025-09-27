package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_play_history.GetPlayProgressQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetPlayProgressQryHandler(
) : Query<GetPlayProgressQry.Request, GetPlayProgressQry.Response> {

    override fun exec(request: GetPlayProgressQry.Request): GetPlayProgressQry.Response {

        return GetPlayProgressQry.Response(

        )
    }
}
