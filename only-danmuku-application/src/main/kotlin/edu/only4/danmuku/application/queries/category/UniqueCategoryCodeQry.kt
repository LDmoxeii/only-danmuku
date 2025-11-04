package edu.only4.danmuku.application.queries.category

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 分类信息;
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/04
 */
object UniqueCategoryCodeQry {

    class Request(
        val code: String,
        val excludeCategoryId: Long?
    ) : RequestParam<Response>

    class Response(
        val exists: Boolean
    )
}
