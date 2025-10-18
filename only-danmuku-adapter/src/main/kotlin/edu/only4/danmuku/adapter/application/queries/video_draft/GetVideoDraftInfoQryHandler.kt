package edu.only4.danmuku.adapter.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_draft.GetVideoDraftInfoQry

import org.springframework.stereotype.Service

/**
 * 获取视频草稿信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoDraftInfoQryHandler(
) : Query<GetVideoDraftInfoQry.Request, GetVideoDraftInfoQry.Response> {

    override fun exec(request: GetVideoDraftInfoQry.Request): GetVideoDraftInfoQry.Response {

        return TODO()
    }
}
