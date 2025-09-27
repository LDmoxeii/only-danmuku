package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_danmuku.GetUserDanmukuHistoryQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetUserDanmukuHistoryQryHandler(
) : Query<GetUserDanmukuHistoryQry.Request, GetUserDanmukuHistoryQry.Response> {

    override fun exec(request: GetUserDanmukuHistoryQry.Request): GetUserDanmukuHistoryQry.Response {

        return GetUserDanmukuHistoryQry.Response(

        )
    }
}
