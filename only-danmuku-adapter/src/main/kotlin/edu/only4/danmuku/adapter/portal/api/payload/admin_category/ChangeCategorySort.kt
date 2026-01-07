package edu.only4.danmuku.adapter.portal.api.payload.admin_category

object ChangeCategorySort {

    data class Request(
        val parentId: Long,
        val categoryIds: String,
    )
}
