package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video.GetVideoPlayFilesQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetVideoPlayFilesQryHandler(
) : Query<GetVideoPlayFilesQry.Request, GetVideoPlayFilesQry.Response> {

    override fun exec(request: GetVideoPlayFilesQry.Request): GetVideoPlayFilesQry.Response {

        return GetVideoPlayFilesQry.Response(

        )
    }
}
