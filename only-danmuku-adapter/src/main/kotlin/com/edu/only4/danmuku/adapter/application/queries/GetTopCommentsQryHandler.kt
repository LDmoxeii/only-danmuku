package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_comment.GetTopCommentsQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetTopCommentsQryHandler(
) : Query<GetTopCommentsQry.Request, GetTopCommentsQry.Response> {

    override fun exec(request: GetTopCommentsQry.Request): GetTopCommentsQry.Response {

        return GetTopCommentsQry.Response(

        )
    }
}
