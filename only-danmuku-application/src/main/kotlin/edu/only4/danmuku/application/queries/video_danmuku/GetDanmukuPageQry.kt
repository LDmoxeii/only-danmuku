package edu.only4.danmuku.application.queries.video_danmuku

import com.only4.cap4k.ddd.core.application.query.PageQueryParam

/**
 * 获取弹幕分页列表(管理端)
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/17
 */
object GetDanmukuPageQry {

    data class Request(
        /** 视频作者ID - 查询该作者所有视频收到的弹幕 */
        val videoUserId: Long? = null,
        /** 视频名称模糊查询 */
        val videoNameFuzzy: String? = null,

        ) : PageQueryParam<Response>()

    data class Response(
        /** 弹幕ID */
        val danmukuId: Long,
        /** 视频ID */
        val videoId: Long,
        /** 视频名称 */
        val videoName: String? = null,
        /** 视频封面 */
        val videoCover: String? = null,
        /** 用户ID */
        val customerId: Long,
        /** 用户昵称 */
        val customerNickname: String? = null,
        /** 弹幕内容 */
        val text: String? = null,
        /** 展示位置 */
        val mode: Int? = null,
        /** 颜色 */
        val color: String? = null,
        /** 展示时间(秒) */
        val time: Int? = null,
        /** 发布时间 */
        val postTime: Long
    )
}
