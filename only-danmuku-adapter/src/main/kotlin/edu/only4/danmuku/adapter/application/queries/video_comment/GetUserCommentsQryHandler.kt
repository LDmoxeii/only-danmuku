package edu.only4.danmuku.adapter.application.queries.video_comment

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_comment.GetUserCommentsQry

import org.springframework.stereotype.Service

/**
 * 获取用户评论列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetUserCommentsQryHandler(
) : Query<GetUserCommentsQry.Request, GetUserCommentsQry.Response> {

    override fun exec(request: GetUserCommentsQry.Request): GetUserCommentsQry.Response {

        return GetUserCommentsQry.Response(

        )
    }
}
