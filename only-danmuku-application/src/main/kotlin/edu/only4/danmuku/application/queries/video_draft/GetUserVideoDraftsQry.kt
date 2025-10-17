package edu.only4.danmuku.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.query.PageQueryParam

/**
 * 获取用户视频草稿列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetUserVideoDraftsQry {

    data class Request(
        /** 用户ID */
        val userId: Long,
        /** 状态(-1进行中) */
        val status: Int? = null,
        /** 视频名称模糊查询 */
        val videoNameFuzzy: String? = null,
        /** 排除状态数组 */
        val excludeStatusArray: List<Int>? = null
    ) : PageQueryParam<Response>()

    data class Response(
        /** 视频ID */
        val videoId: Long,
        /** 视频封面 */
        val videoCover: String?,
        /** 视频名称 */
        val videoName: String?,
        /** 创建时间 */
        val createTime: Long,
        /** 最后更新时间 */
        val lastUpdateTime: Long?,
        /** 状态 */
        val status: Int,
        /** 播放数 */
        val playCount: Int? = 0,
        /** 点赞数 */
        val likeCount: Int? = 0,
        /** 弹幕数 */
        val danmuCount: Int? = 0,
        /** 评论数 */
        val commentCount: Int? = 0,
        /** 投币数 */
        val coinCount: Int? = 0,
        /** 收藏数 */
        val collectCount: Int? = 0
    )
}
