package edu.only4.danmuku.adapter.application.queries.video_comment

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData

import edu.only4.danmuku.application.queries.video_comment.VideoCommentPageQry

import org.springframework.stereotype.Service

/**
 * 评论分页
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class VideoCommentPageQryHandler(
) : PageQuery<VideoCommentPageQry.Request, VideoCommentPageQry.Response> {

    override fun exec(request: VideoCommentPageQry.Request): PageData<VideoCommentPageQry.Response> {
        TODO()
    }
}
