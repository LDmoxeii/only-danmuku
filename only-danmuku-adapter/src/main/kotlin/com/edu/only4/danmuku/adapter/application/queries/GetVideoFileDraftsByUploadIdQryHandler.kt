package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_file_draft.GetVideoFileDraftsByUploadIdQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetVideoFileDraftsByUploadIdQryHandler(
) : Query<GetVideoFileDraftsByUploadIdQry.Request, GetVideoFileDraftsByUploadIdQry.Response> {

    override fun exec(request: GetVideoFileDraftsByUploadIdQry.Request): GetVideoFileDraftsByUploadIdQry.Response {

        return GetVideoFileDraftsByUploadIdQry.Response(

        )
    }
}
