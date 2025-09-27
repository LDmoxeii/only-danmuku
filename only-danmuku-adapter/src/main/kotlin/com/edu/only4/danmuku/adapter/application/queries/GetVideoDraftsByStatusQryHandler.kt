package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_draft.GetVideoDraftsByStatusQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetVideoDraftsByStatusQryHandler(
) : Query<GetVideoDraftsByStatusQry.Request, GetVideoDraftsByStatusQry.Response> {

    override fun exec(request: GetVideoDraftsByStatusQry.Request): GetVideoDraftsByStatusQry.Response {

        return GetVideoDraftsByStatusQry.Response(

        )
    }
}
