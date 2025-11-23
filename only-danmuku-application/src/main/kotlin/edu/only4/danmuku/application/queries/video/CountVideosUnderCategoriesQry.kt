package edu.only4.danmuku.application.queries.video

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 统计分类及其子分类下的视频数量
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/22
 */
object CountVideosUnderCategoriesQry {

    /**
     * 请求统计给定分类列表（含其所有子级）下的视频数量。
     *
     * @param categoryIds 待检查的分类ID列表，通常只传一个根分类 ID
     */
    data class Request(
        var categoryIds: Collection<Long> = emptyList()
    ) : RequestParam<Response>

    /**
     * 统计结果
     *
     * @param totalCount 分类树下绑定的视频总数
     */
    data class Response(
        val totalCount: Long
    )
}
