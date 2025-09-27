package com.edu.only4.danmuku.adapter.application.queries

import com.edu.only4.danmuku.application.queries.video_file_draft.GetVideoFileDraftsByTransferStatusQry
import com.only4.cap4k.ddd.core.application.query.Query
import org.springframework.stereotype.Service

@Service
class GetVideoFileDraftsByTransferStatusQryHandler(
) : Query<GetVideoFileDraftsByTransferStatusQry.Request, GetVideoFileDraftsByTransferStatusQry.Response> {

    override fun exec(request: GetVideoFileDraftsByTransferStatusQry.Request): GetVideoFileDraftsByTransferStatusQry.Response {

        return GetVideoFileDraftsByTransferStatusQry.Response(

        )
    }
}
