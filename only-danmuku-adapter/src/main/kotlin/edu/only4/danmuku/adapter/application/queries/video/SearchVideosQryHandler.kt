package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.application.query.Query
import com.only4.cap4k.ddd.core.share.PageData

import edu.only4.danmuku.application.queries.video.SearchVideosQry

import org.springframework.stereotype.Service

/**
 * 搜索视频
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class SearchVideosQryHandler(
) : PageQuery<SearchVideosQry.Request, SearchVideosQry.Response> {

    override fun exec(request: SearchVideosQry.Request): PageData<SearchVideosQry.Response> {

        return TODO()
    }
}
