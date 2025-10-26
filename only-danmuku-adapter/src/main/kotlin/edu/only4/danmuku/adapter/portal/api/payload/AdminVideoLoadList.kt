package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam
import java.time.LocalDateTime

/**
 * 加载视频列表(分页)接口载荷
 */
object AdminVideoLoadList {

    class Request(
        /** 视频名称模糊查询 */
        val videoNameFuzzy: String? = null,
        /** 父分类Id */
        val categoryParentId: Long? = null,
        /** 分类Id */
        val categoryId: Long? = null,
        /** 视频状态 */
        val recommendType: Int? = null,
    ) : PageParam()

    /**
     * 视频项
     */
    data class VideoItem(
        var videoId: String? = null,
        var videoCover: String? = null,
        var videoName: String? = null,
        var userId: String? = null,
        var nickName: String? = null,
        var duration: Int? = null,
        var status: Int,
        var statusName: String,
        var createTime: LocalDateTime? = null,
        var lastUpdateTime: LocalDateTime? = null,
        var playCount: Int? = null,
        var likeCount: Int? = null,
        var danmuCount: Int? = null,
        var commentCount: Int? = null,
        var coinCount: Int? = null,
        var collectCount: Int? = null,
        var recommendType: Int? = null,
    )
}
