package edu.only4.danmuku.adapter.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_draft.GetVideoDraftCountByStatusQry

import org.springframework.stereotype.Service

/**
 * 按状态统计视频草稿数量
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoDraftCountByStatusQryHandler(
) : Query<GetVideoDraftCountByStatusQry.Request, GetVideoDraftCountByStatusQry.Response> {

    override fun exec(request: GetVideoDraftCountByStatusQry.Request): GetVideoDraftCountByStatusQry.Response {

        return TODO()
    }
}
