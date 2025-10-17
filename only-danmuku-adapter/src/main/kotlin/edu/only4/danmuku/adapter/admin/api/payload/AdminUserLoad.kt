package edu.only4.danmuku.adapter.admin.api.payload

/**
 * 加载用户列表(分页)接口载荷
 */
object AdminUserLoad {

    data class Request(
        /** 页码 */
        val pageNo: Int? = null,
        /** 昵称模糊查询 */
        val nickNameFuzzy: String? = null,
        /** 用户状态 */
        val status: Int? = null
    )

    data class Response(
        /** 用户列表 */
        var list: List<Any>? = null,
        var pageNo: Int? = null,
        var totalCount: Int? = null
    )
}
