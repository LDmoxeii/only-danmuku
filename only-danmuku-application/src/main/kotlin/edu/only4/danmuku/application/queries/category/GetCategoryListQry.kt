package edu.only4.danmuku.application.queries.category

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 获取分类列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetCategoryListQry {

    data class Request(
        /** 父分类ID，如果为null则返回所有分类 */
        val parentId: Long? = null
    ) : ListQueryParam<Response>

    data class Response(
        /** 分类ID */
        val categoryId: Long,
        /** 分类编码 */
        val code: String,
        /** 分类名称 */
        val name: String,
        /** 父分类ID */
        val parentId: Long,
        /** 图标 */
        val icon: String? = null,
        /** 背景图 */
        val background: String? = null,
        /** 排序号 */
        val sort: Int,
    )
}
