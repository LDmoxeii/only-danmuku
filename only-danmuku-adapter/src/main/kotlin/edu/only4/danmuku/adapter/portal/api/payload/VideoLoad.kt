package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载视频列表(按分类)接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.VideoController.videoLoad
 */
object VideoLoad {

    /**
     * 请求参数
     */
    data class Request(
        /**
         * 父分类ID
         */
        val pCategoryId: Int? = null,
        /**
         * 分类ID
         */
        val categoryId: Int? = null,
        /**
         * 页码
         */
        val pageNo: Int? = null
    )

    /**
     * 响应结果
     */
    data class Response(
        /**
         * 视频列表
         */
        var list: List<Any>? = null,
        /**
         * 当前页码
         */
        var pageNo: Int? = null,
        /**
         * 总记录数
         */
        var totalCount: Int? = null
    )
}
