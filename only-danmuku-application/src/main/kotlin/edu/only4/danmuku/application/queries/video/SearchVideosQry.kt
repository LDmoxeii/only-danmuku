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
        /** 视频时长(秒) */
        val duration: Int? = null,
        /** 视频状态 */
        val status: Int? = null,
        /** 创建时间 */
        val createTime: Long,
        /** 最后更新时间 */
        val lastUpdateTime: Long? = null,
        /** 播放数 */
        val playCount: Int? = null,
        /** 点赞数 */
        val likeCount: Int? = null,
        /** 弹幕数 */
        val danmuCount: Int? = null,
        /** 评论数 */
        val commentCount: Int? = null,
        /** 投币数 */
        val coinCount: Int? = null,
        /** 收藏数 */
        val collectCount: Int? = null,
        /** 推荐类型 */
        val recommendType: Int? = null,
    )
}
