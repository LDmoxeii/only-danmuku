package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video.GetVideosByCategoryQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetVideosByCategoryQryHandler(
) : Query<GetVideosByCategoryQry.Request, GetVideosByCategoryQry.Response> {

    override fun exec(request: GetVideosByCategoryQry.Request): GetVideosByCategoryQry.Response {

        return GetVideosByCategoryQry.Response(

        )
    }
}
