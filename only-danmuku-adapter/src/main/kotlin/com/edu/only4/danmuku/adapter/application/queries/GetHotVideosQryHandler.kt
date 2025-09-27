package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video.GetHotVideosQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetHotVideosQryHandler(
) : Query<GetHotVideosQry.Request, GetHotVideosQry.Response> {

    override fun exec(request: GetHotVideosQry.Request): GetHotVideosQry.Response {

        return GetHotVideosQry.Response(

        )
    }
}
