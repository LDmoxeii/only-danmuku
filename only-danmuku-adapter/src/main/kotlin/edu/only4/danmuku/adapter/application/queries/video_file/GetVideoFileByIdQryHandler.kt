package edu.only4.danmuku.adapter.application.queries.video_file

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_file.GetVideoFileByIdQry

import org.springframework.stereotype.Service

/**
 * 根据ID获取视频文件
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoFileByIdQryHandler(
) : Query<GetVideoFileByIdQry.Request, GetVideoFileByIdQry.Response> {

    override fun exec(request: GetVideoFileByIdQry.Request): GetVideoFileByIdQry.Response {

        return GetVideoFileByIdQry.Response(

        )
    }
}
