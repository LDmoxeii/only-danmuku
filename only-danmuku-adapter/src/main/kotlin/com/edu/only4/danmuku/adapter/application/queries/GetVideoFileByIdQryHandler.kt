package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_file.GetVideoFileByIdQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetVideoFileByIdQryHandler(
) : Query<GetVideoFileByIdQry.Request, GetVideoFileByIdQry.Response> {

    override fun exec(request: GetVideoFileByIdQry.Request): GetVideoFileByIdQry.Response {

        return GetVideoFileByIdQry.Response(

        )
    }
}
