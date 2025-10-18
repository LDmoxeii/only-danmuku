package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.ListQuery
import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.application.query.Query
import com.only4.cap4k.ddd.core.share.PageData

import edu.only4.danmuku.application.queries.video.GetVideosByCategoryQry

import org.springframework.stereotype.Service

/**
 * 按分类获取视频列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideosByCategoryQryHandler(
) : PageQuery<GetVideosByCategoryQry.Request, GetVideosByCategoryQry.Response> {

    override fun exec(request: GetVideosByCategoryQry.Request): PageData<GetVideosByCategoryQry.Response> {

        return TODO()
    }
}
