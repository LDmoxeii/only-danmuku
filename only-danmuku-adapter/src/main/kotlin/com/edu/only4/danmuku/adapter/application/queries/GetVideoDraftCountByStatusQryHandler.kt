package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_draft.GetVideoDraftCountByStatusQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetVideoDraftCountByStatusQryHandler(
) : Query<GetVideoDraftCountByStatusQry.Request, GetVideoDraftCountByStatusQry.Response> {

    override fun exec(request: GetVideoDraftCountByStatusQry.Request): GetVideoDraftCountByStatusQry.Response {

        return GetVideoDraftCountByStatusQry.Response(

        )
    }
}
