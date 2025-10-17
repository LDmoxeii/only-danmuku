package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载所有视频接口载荷
 */
object UCenterLoadAllVideo {

    class Request

    /**
     * 视频项
     */
    data class VideoItem(
        /** 视频ID */
        var videoId: String? = null,
        /** 视频封面 */
        var videoCover: String? = null,
        /** 视频名称 */
        var videoName: String? = null,
        /** 创建时间 */
        var createTime: String? = null
    )
}
