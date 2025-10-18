package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.ListQuery
import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video.GetRecommendVideosQry

import org.springframework.stereotype.Service

/**
 * 获取推荐视频列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetRecommendVideosQryHandler(
) : ListQuery<GetRecommendVideosQry.Request, GetRecommendVideosQry.Response> {

    override fun exec(request: GetRecommendVideosQry.Request): List<GetRecommendVideosQry.Response> {

        return listOf()
    }
}
