package edu.only4.danmuku.adapter.application.queries.video_file_draft

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_file_draft.GetVideoFileDraftsByUploadIdQry

import org.springframework.stereotype.Service

/**
 * 根据上传ID获取文件草稿
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoFileDraftsByUploadIdQryHandler(
) : Query<GetVideoFileDraftsByUploadIdQry.Request, GetVideoFileDraftsByUploadIdQry.Response> {

    override fun exec(request: GetVideoFileDraftsByUploadIdQry.Request): GetVideoFileDraftsByUploadIdQry.Response {

        return GetVideoFileDraftsByUploadIdQry.Response(

        )
    }
}
