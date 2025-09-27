package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_file.GetVideoFilesQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetVideoFilesQryHandler(
) : Query<GetVideoFilesQry.Request, GetVideoFilesQry.Response> {

    override fun exec(request: GetVideoFilesQry.Request): GetVideoFilesQry.Response {

        return GetVideoFilesQry.Response(

        )
    }
}
