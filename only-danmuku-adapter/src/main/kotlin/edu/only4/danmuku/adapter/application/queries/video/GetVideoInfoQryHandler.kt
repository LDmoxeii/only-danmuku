package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video.GetVideoInfoQry

import org.springframework.stereotype.Service

/**
 * 获取视频信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoInfoQryHandler(
) : Query<GetVideoInfoQry.Request, GetVideoInfoQry.Response> {

    override fun exec(request: GetVideoInfoQry.Request): GetVideoInfoQry.Response {

        return GetVideoInfoQry.Response(

        )
    }
}
