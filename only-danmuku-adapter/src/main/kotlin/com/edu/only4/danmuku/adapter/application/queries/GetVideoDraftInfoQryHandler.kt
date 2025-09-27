package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_draft.GetVideoDraftInfoQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetVideoDraftInfoQryHandler(
) : Query<GetVideoDraftInfoQry.Request, GetVideoDraftInfoQry.Response> {

    override fun exec(request: GetVideoDraftInfoQry.Request): GetVideoDraftInfoQry.Response {

        return GetVideoDraftInfoQry.Response(

        )
    }
}
