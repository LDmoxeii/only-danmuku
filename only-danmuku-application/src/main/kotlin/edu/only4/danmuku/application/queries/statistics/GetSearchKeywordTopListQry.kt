package edu.only4.danmuku.application.queries.statistics

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 获取搜索关键词排行榜
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
object GetSearchKeywordTopListQry {

    class Request : ListQueryParam<Item>

    data class Item(
        val keyword: String
    )
}
