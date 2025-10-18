package edu.only4.danmuku.application.queries.video

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取推荐视频列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetRecommendVideosQry {

    class Request : RequestParam<List<Response>>

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
