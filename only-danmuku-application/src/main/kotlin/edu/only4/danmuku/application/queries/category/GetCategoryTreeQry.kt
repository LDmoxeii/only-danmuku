package edu.only4.danmuku.application.queries.category

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 获取分类树形结构
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetCategoryTreeQry {

    class Request : ListQueryParam<Response>

    /**
     * 分类树节点
     */
    data class Response(
        val categoryId: Long,
        val code: String,
        val name: String,
        val parentId: Long,
        val icon: String? = null,
        val background: String? = null,
        val sort: Int,
        val children: List<Response> = emptyList(),
    )
}
