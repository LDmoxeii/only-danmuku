package edu.only4.danmuku.adapter.application.queries.video_file

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_file.GetVideoFilesQry

import org.springframework.stereotype.Service

/**
 * 获取视频文件列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoFilesQryHandler(
) : Query<GetVideoFilesQry.Request, GetVideoFilesQry.Response> {

    override fun exec(request: GetVideoFilesQry.Request): GetVideoFilesQry.Response {

        return GetVideoFilesQry.Response(

        )
    }
}
