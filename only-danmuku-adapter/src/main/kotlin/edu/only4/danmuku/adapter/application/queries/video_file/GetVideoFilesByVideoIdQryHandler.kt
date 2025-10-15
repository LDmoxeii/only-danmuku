package edu.only4.danmuku.adapter.application.queries.video_file

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_file.GetVideoFilesByVideoIdQry

import org.springframework.stereotype.Service

/**
 * 根据视频ID获取文件列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoFilesByVideoIdQryHandler(
) : Query<GetVideoFilesByVideoIdQry.Request, GetVideoFilesByVideoIdQry.Response> {

    override fun exec(request: GetVideoFilesByVideoIdQry.Request): GetVideoFilesByVideoIdQry.Response {

        return GetVideoFilesByVideoIdQry.Response(

        )
    }
}
