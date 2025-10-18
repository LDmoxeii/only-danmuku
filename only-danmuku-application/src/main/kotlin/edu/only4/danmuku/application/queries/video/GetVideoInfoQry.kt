package edu.only4.danmuku.application.queries.video

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取视频信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetVideoInfoQry {

    data class Request(
        /** 视频ID */
        val videoId: Long
    ) : RequestParam<Response>

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
        /** 视频简介 */
        val introduction: String? = null,
        /** 互动设置 */
        val interaction: String? = null,
        /** 视频时长(秒) */
        val duration: Int? = null,
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
        /** 创建时间 */
        val createTime: Long
    )
}
