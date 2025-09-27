package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_comment.GetUserCommentsQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetUserCommentsQryHandler(
) : Query<GetUserCommentsQry.Request, GetUserCommentsQry.Response> {

    override fun exec(request: GetUserCommentsQry.Request): GetUserCommentsQry.Response {

        return GetUserCommentsQry.Response(

        )
    }
}
