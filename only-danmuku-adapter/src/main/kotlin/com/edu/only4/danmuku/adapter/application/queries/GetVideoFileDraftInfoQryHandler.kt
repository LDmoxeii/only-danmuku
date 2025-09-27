package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_file_draft.GetVideoFileDraftInfoQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetVideoFileDraftInfoQryHandler(
) : Query<GetVideoFileDraftInfoQry.Request, GetVideoFileDraftInfoQry.Response> {

    override fun exec(request: GetVideoFileDraftInfoQry.Request): GetVideoFileDraftInfoQry.Response {

        return GetVideoFileDraftInfoQry.Response(

        )
    }
}
