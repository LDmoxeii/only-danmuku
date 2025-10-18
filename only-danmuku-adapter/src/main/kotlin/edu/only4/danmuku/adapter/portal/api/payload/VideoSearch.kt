package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 搜索视频接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.VideoController.videoSearch
 */
object VideoSearch {

    /**
     * 请求参数
     */
    data class Request(
        /** 搜索关键词 */
        @field:NotEmpty(message = "搜索关键词不能为空")
        val keyword: String = "",
        /** 排序类型 */
        val orderType: Int? = null,
        /** 页码 */
        val pageNo: Int? = null
    )

    /**
     * 响应结果 - 分页搜索结果
     */
    data class Response(
        /** 搜索结果列表 */
        var list: List<VideoItem>? = null,
        /** 当前页码 */
        var pageNo: Int? = null,
        /** 总记录数 */
        var totalCount: Int? = null
    )

    data class VideoItem(
        var videoId: String? = null,
        var videoCover: String? = null,
        var videoName: String? = null,
        var userId: String? = null,
        var nickName: String? = null,
        var avatar: String? = null,
        var playCount: Int? = null,
        var likeCount: Int? = null,
        var createTime: String? = null
    )
}
