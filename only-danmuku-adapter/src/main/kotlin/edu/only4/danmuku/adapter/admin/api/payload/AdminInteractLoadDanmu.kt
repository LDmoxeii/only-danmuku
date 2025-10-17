package edu.only4.danmuku.adapter.admin.api.payload

/**
 * 加载弹幕列表(分页)接口载荷
 */
object AdminInteractLoadDanmu {

    data class Request(
        /** 页码 */
        val pageNo: Int? = null,
        /** 视频名称模糊查询 */
        val videoNameFuzzy: String? = null
    )

    data class Response(
        /** 弹幕列表 */
        var list: List<Any>? = null,
        var pageNo: Int? = null,
        var totalCount: Int? = null
    )
}
