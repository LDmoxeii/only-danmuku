package edu.only4.danmuku.adapter.admin.api.payload

/**
 * 加载视频列表(分页)接口载荷
 */
object AdminVideoLoadList {

    data class Request(
        /** ��码 */
        val pageNo: Int? = null,
        /** 视频名称模糊查询 */
        val videoNameFuzzy: String? = null,
        /** 视频状态 */
        val status: Int? = null
    )

    data class Response(
        /** 视频列表 */
        var list: List<Any>? = null,
        var pageNo: Int? = null,
        var pageSize: Int? = null,
        var totalCount: Int? = null
    )
}
