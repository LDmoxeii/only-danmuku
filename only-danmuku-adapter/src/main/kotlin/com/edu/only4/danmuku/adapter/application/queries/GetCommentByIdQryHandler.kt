package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_comment.GetCommentByIdQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetCommentByIdQryHandler(
) : Query<GetCommentByIdQry.Request, GetCommentByIdQry.Response> {

    override fun exec(request: GetCommentByIdQry.Request): GetCommentByIdQry.Response {

        return GetCommentByIdQry.Response(

        )
    }
}
