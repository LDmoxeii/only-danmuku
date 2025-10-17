package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam

/**
 * 加载用户发布的视频列表接口载荷
 */
object UCenterLoadVideoList {

    data class Request(
        /** 状态(-1进行中, 0转码中, 1待审核, 2审核中, 3审核通过, 4审核不通过) */
        val status: Int? = null,
        /** 视频名称模糊查询 */
        val videoNameFuzzy: String? = null
    ) : PageParam()

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
        var createTime: String? = null,
        /** 最后更新时间 */
        var lastUpdateTime: String? = null,
        /** 状态 */
        var status: Int? = null,
        /** 播放数 */
        var playCount: Int? = null,
        /** 点赞数 */
        var likeCount: Int? = null,
        /** 弹幕数 */
        var danmuCount: Int? = null,
        /** 评论数 */
        var commentCount: Int? = null,
        /** 投币数 */
        var coinCount: Int? = null,
        /** 收藏数 */
        var collectCount: Int? = null
    )
}
