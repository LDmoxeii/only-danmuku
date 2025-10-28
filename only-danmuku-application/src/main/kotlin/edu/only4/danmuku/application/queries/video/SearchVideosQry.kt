package edu.only4.danmuku.application.queries.video

import com.only4.cap4k.ddd.core.application.query.PageQueryParam

/**
 * 搜索视频
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object SearchVideosQry {

    data class Request(
        /** 用户ID - 过滤指定用户的视频 */
        val userId: Long? = null,
        /** 父分类Id */
        val categoryParentId: Long? = null,
        /** 分类Id */
        val categoryId: Long? = null,
        /** 视频名称模糊查询 */
        val videoNameFuzzy: String? = null,
        /** 视频状态 */
        val recommendType: Int? = null,
        /** 排除的视频ID列表（可选） */
        val excludeVideoIds: List<Long>? = null,
    ) : PageQueryParam<Response>()

    data class Response(
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
