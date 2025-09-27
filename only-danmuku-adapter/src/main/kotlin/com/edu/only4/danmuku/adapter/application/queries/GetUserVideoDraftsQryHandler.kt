package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_draft.GetUserVideoDraftsQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetUserVideoDraftsQryHandler(
) : Query<GetUserVideoDraftsQry.Request, GetUserVideoDraftsQry.Response> {

    override fun exec(request: GetUserVideoDraftsQry.Request): GetUserVideoDraftsQry.Response {

        return GetUserVideoDraftsQry.Response(

        )
    }
}
