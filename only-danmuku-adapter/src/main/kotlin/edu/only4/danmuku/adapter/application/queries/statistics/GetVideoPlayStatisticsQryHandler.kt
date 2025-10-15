package edu.only4.danmuku.adapter.application.queries.statistics

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.statistics.GetVideoPlayStatisticsQry

import org.springframework.stereotype.Service

/**
 * 获取视频播放统计
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoPlayStatisticsQryHandler(
) : Query<GetVideoPlayStatisticsQry.Request, GetVideoPlayStatisticsQry.Response> {

    override fun exec(request: GetVideoPlayStatisticsQry.Request): GetVideoPlayStatisticsQry.Response {

        return GetVideoPlayStatisticsQry.Response(

        )
    }
}
