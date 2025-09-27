package com.edu.only4.danmuku.adapter.application.queries;

import com.edu.only4.danmuku.application.queries.video_comment.VideoCommentPageQry
import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import org.springframework.stereotype.Service

@Service
class VideoCommentPageQryHandler(
) : PageQuery<VideoCommentPageQry.Request, VideoCommentPageQry.Response> {

    override fun exec(request: VideoCommentPageQry.Request): PageData<VideoCommentPageQry.Response > {

        return PageData.empty()

    }
}
