package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_comment.GetCommentRepliesQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetCommentRepliesQryHandler(
) : Query<GetCommentRepliesQry.Request, GetCommentRepliesQry.Response> {

    override fun exec(request: GetCommentRepliesQry.Request): GetCommentRepliesQry.Response {

        return GetCommentRepliesQry.Response(

        )
    }
}
