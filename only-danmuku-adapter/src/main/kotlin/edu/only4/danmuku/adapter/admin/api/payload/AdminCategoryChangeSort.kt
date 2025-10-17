package edu.only4.danmuku.adapter.admin.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 调整分类排序接口载荷
 */
object AdminCategoryChangeSort {

    data class Request(
        /** 父分类ID */

        @field:NotEmpty(message = "父分类ID不能为空")
        val pCategoryId: Int = 0,
        /** 分类ID列表(逗号分隔) */

        @field:NotEmpty(message = "分类ID列表(逗号分隔)不能为空")
        val categoryIds: String = ""
    )

    class Response
}
