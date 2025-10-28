package edu.only4.danmuku.application.queries.video_danmuku

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 根据文件ID获取弹幕
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetDanmukuListByFileIdQry {

    data class Request(
        val fileId: Long,
        val videoId: Long
    ) : ListQueryParam<Response>

    data class Response(
        val danmukuId: Long,
        val fileId: Long,
        val videoId: Long,
        val userId: Long,
        val text: String,
        val mode: Int,
        val color: String,
        val time: Int,
        val postTime: Long,
        val videoName: String? = null,
        val videoCover: String? = null,
        val nickName: String? = null,
    )
}
