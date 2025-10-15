package edu.only4.danmuku.adapter.application.queries.video_comment

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_comment.GetCommentCountQry

import org.springframework.stereotype.Service

/**
 * 获取评论数量
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCommentCountQryHandler(
) : Query<GetCommentCountQry.Request, GetCommentCountQry.Response> {

    override fun exec(request: GetCommentCountQry.Request): GetCommentCountQry.Response {

        return GetCommentCountQry.Response(

        )
    }
}
