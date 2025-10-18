package edu.only4.danmuku.adapter.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.application.query.Query
import com.only4.cap4k.ddd.core.share.PageData

import edu.only4.danmuku.application.queries.video_draft.GetUserVideoDraftsQry

import org.springframework.stereotype.Service

/**
 * 获取用户视频草稿列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetUserVideoDraftsQryHandler(
) : PageQuery<GetUserVideoDraftsQry.Request, GetUserVideoDraftsQry.Response> {

    override fun exec(request: GetUserVideoDraftsQry.Request): PageData<GetUserVideoDraftsQry.Response> {

        return PageData.empty()
    }
}
