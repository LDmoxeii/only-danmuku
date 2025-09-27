package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video.GetVideoInfoQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetVideoInfoQryHandler(
) : Query<GetVideoInfoQry.Request, GetVideoInfoQry.Response> {

    override fun exec(request: GetVideoInfoQry.Request): GetVideoInfoQry.Response {

        return GetVideoInfoQry.Response(

        )
    }
}
