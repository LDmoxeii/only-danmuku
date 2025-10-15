package edu.only4.danmuku.adapter.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_draft.GetVideoDraftsByStatusQry

import org.springframework.stereotype.Service

/**
 * 按状态获取视频草稿
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoDraftsByStatusQryHandler(
) : Query<GetVideoDraftsByStatusQry.Request, GetVideoDraftsByStatusQry.Response> {

    override fun exec(request: GetVideoDraftsByStatusQry.Request): GetVideoDraftsByStatusQry.Response {

        return GetVideoDraftsByStatusQry.Response(

        )
    }
}
