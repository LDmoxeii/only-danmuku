package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video.GetVideoPlayFilesQry

import org.springframework.stereotype.Service

/**
 * 获取视频播放文件列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoPlayFilesQryHandler(
) : Query<GetVideoPlayFilesQry.Request, GetVideoPlayFilesQry.Response> {

    override fun exec(request: GetVideoPlayFilesQry.Request): GetVideoPlayFilesQry.Response {

        return GetVideoPlayFilesQry.Response(

        )
    }
}
