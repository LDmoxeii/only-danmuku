package edu.only4.danmuku.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.query.PageQueryParam

/**
 * 获取用户视频稿件列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetUserVideoPostQry {

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
        val videoPostId: Long,
        val videoId: Long?,
        val videoCover: String,
        val videoName: String,
        val duration: Int?,
        val createTime: Long,
        val lastUpdateTime: Long?,
        val status: Int,
        val interaction: String? = null,
        val playCount: Int? = 0,
        val likeCount: Int? = 0,
        val danmuCount: Int? = 0,
        val commentCount: Int? = 0,
        val coinCount: Int? = 0,
        val collectCount: Int? = 0,
    )
}
