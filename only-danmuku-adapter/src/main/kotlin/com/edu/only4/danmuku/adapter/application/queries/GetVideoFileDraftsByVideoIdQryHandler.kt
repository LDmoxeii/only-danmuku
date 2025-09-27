package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_file_draft.GetVideoFileDraftsByVideoIdQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetVideoFileDraftsByVideoIdQryHandler(
) : Query<GetVideoFileDraftsByVideoIdQry.Request, GetVideoFileDraftsByVideoIdQry.Response> {

    override fun exec(request: GetVideoFileDraftsByVideoIdQry.Request): GetVideoFileDraftsByVideoIdQry.Response {

        return GetVideoFileDraftsByVideoIdQry.Response(

        )
    }
}
