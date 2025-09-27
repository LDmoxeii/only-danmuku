package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_file.GetVideoFilesByVideoIdQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetVideoFilesByVideoIdQryHandler(
) : Query<GetVideoFilesByVideoIdQry.Request, GetVideoFilesByVideoIdQry.Response> {

    override fun exec(request: GetVideoFilesByVideoIdQry.Request): GetVideoFilesByVideoIdQry.Response {

        return GetVideoFilesByVideoIdQry.Response(

        )
    }
}
