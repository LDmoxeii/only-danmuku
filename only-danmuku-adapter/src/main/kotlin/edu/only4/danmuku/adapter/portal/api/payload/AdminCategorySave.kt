package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.PositiveOrZero

/**
 * 保存/更新分类接口载荷
 */
object AdminCategorySave {

    data class Request(
        /** 父分类ID */
        @field:PositiveOrZero(message = "父分类ID必须大于等于0")
        val pCategoryId: Long,

        /** 分类ID(更新时传) */
        val categoryId: Long?,

        /** 分类编码 */
        @field:NotEmpty(message = "分类编码不能为空")
        val categoryCode: String,

        /** 分类名称 */
        @field:NotEmpty(message = "分类名称不能为空")
        val categoryName: String,

        /** 分类图标 */
        val icon: String? = null,

        /** 分类背景 */
        val background: String? = null
    )

    class Response
}
