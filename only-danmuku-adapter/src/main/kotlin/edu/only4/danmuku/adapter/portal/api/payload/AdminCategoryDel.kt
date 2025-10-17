package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 删除分类接口载荷
 */
object AdminCategoryDel {

    data class Request(
        /** 分类ID */

        @field:NotEmpty(message = "分类ID不能为空")
        val categoryId: Int = 0
    )

    class Response
}
