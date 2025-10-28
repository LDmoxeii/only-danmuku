package edu.only4.danmuku.application.queries.video

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 获取所有视频
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetVideoAllList {

    data class Request(
        val userId: Long,
    ) : ListQueryParam<VideoItem>

    data class VideoItem(
        var videoId: Long,
        var videoCover: String?,
        var videoName: String?,
        var userId: Long?,
        var createTime: Long,
        var lastUpdateTime: Long?,
        var parentCategoryId: Long,
        var categoryId: Long?,
        var postType: Int,
        var originInfo: String?,
        var tags: String?,
        var introduction: String?,
        var duration: Int,
        val status: Int,
        var playCount: Int,
        var likeCount: Int,
        var danmuCount: Int,
        var commentCount: Int,
        var coinCount: Int,
        var collectCount: Int,
        var recommendType: Int,
        var lastPlayTime: Long?,
        var nickName: String? = null,
        var avatar: String? = null,
        var categoryFullName: String?,
    )
}
