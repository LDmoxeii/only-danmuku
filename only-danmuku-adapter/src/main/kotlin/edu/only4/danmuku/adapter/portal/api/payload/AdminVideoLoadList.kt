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
        /** 视频ID */
        var videoId: String? = null,
        /** 视频封面 */
        var videoCover: String? = null,
        /** 视频名称 */
        var videoName: String? = null,
        /** 作者ID */
        var userId: String? = null,
        /** 作者昵称 */
        var nickName: String? = null,
        /** 视频时长(秒) */
        var duration: Int? = null,
        /** 视频状态: 0-转码中 1-转码成功 2-转码失败 */
        var status: Int? = null,
        /** 创建时间 */
        var createTime: LocalDateTime? = null,
        /** 最后更新时间 */
        var lastUpdateTime: LocalDateTime? = null,
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
        var collectCount: Int? = null,
        /** 推荐类型: 0-不推荐 1-推荐 */
        var recommendType: Int? = null
    )
}
