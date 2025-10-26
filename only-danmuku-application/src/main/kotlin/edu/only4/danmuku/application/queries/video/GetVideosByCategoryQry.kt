package edu.only4.danmuku.application.queries.video

import com.only4.cap4k.ddd.core.application.query.PageQueryParam

/**
 * 按分类获取视频列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetVideosByCategoryQry {

    data class Request(
        /** 父分类ID */
        val pCategoryId: Long? = null,
        /** 分类ID */
        val categoryId: Long? = null,
        /** 推荐类型 (0:非推荐, 1:推荐) */
        val recommendType: Int? = null,
    ) : PageQueryParam<Response>()

    data class Response(
        /** 视频ID */
        val videoId: Long,
        /** 视频封面 */
        val videoCover: String? = null,
        /** 视频名称 */
        val videoName: String? = null,
        /** 作者ID */
        val userId: Long,
        /** 作者昵称 */
        val nickName: String? = null,
        /** 作者头像 */
        val avatar: String? = null,
        /** 播放数 */
        val playCount: Int? = null,
        /** 点赞数 */
        val likeCount: Int? = null,
        /** 创建时间 */
        val createTime: Long
    )
}
