package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_comment.GetCommentCountQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetCommentCountQryHandler(
) : Query<GetCommentCountQry.Request, GetCommentCountQry.Response> {

    override fun exec(request: GetCommentCountQry.Request): GetCommentCountQry.Response {

        return GetCommentCountQry.Response(

        )
    }
}
