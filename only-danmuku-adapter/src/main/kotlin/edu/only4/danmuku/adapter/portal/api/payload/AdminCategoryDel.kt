package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.PositiveOrZero

/**
 * 删除分类接口载荷
 */
object AdminCategoryDel {

    data class Request(
        /** 分类ID */
        @field:PositiveOrZero(message = "分类ID必须大于等于0")
        val categoryId: Long = 0
    )

    class Response
}
