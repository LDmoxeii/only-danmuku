package edu.only4.danmuku.adapter.application.queries.video_comment

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_comment.GetTopCommentsQry

import org.springframework.stereotype.Service

/**
 * 获取置顶评论
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetTopCommentsQryHandler(
) : Query<GetTopCommentsQry.Request, GetTopCommentsQry.Response> {

    override fun exec(request: GetTopCommentsQry.Request): GetTopCommentsQry.Response {

        return GetTopCommentsQry.Response(

        )
    }
}
