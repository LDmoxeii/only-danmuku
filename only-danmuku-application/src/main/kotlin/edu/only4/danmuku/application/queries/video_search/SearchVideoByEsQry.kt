package edu.only4.danmuku.application.queries.video_search

import com.only4.cap4k.ddd.core.application.query.PageQueryParam

/**
 * 使用 ES 搜索视频（替代数据库模糊查询）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/26
 */
object SearchVideoByEsQry {

    data class Request(
        val keyword: String,
        val orderType: Int? = null
    ) : PageQueryParam<Response>()

    data class Response(
        val videoId: Long,
        val videoCover: String?,
        val videoName: String?,
        val userId: Long?,
        val createTime: Long,
        val lastUpdateTime: Long?,
        val parentCategoryId: Long,
        val categoryId: Long?,
        val postType: Int,
        val originInfo: String?,
        val tags: String?,
        val introduction: String?,
        val duration: Int,
        val playCount: Int,
        val likeCount: Int,
        val danmuCount: Int,
        val commentCount: Int,
        val coinCount: Int,
        val collectCount: Int,
        val recommendType: Int,
        val lastPlayTime: Long?,
        val nickName: String?,
        val avatar: String?,
        val categoryFullName: String?
    )
}
