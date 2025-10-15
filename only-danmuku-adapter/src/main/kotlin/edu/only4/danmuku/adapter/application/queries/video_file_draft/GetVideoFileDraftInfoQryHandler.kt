package edu.only4.danmuku.adapter.application.queries.video_file_draft

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_file_draft.GetVideoFileDraftInfoQry

import org.springframework.stereotype.Service

/**
 * 获取视频文件草稿信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoFileDraftInfoQryHandler(
) : Query<GetVideoFileDraftInfoQry.Request, GetVideoFileDraftInfoQry.Response> {

    override fun exec(request: GetVideoFileDraftInfoQry.Request): GetVideoFileDraftInfoQry.Response {

        return GetVideoFileDraftInfoQry.Response(

        )
    }
}
