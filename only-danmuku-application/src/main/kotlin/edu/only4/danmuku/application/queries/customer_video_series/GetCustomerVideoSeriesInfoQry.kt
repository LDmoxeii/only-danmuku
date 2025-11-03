package edu.only4.danmuku.application.queries.customer_video_series

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取用户视频系列信息
 *
 * 该文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetCustomerVideoSeriesInfoQry {

    data class Request(
        /** 系列ID */
        val seriesId: Long
    ) : RequestParam<Response>

    data class Response(
        /** 系列ID */
        val seriesId: Long,
        /** 用户ID */
        val userId: Long,
        /** 系列名称 */
        val seriesName: String? = null,
        /** 系列描述 */
        val seriesDescription: String? = null,
        /** 系列排序 */
        val sort: Int? = null,
        /** 创建时间(秒) */
        val createTime: Long,
        /** 更新时间(秒，仅用于前端展示) */
        val updateTime: Long? = null,
        /** 视频列表 */
        val videoList: List<VideoItem>? = null
    )

    data class VideoItem(
        val videoId: Long,
        val videoCover: String,
        val videoName: String,
        val playCount: Int,
        val sort: Int,
        val createTime: Long,
    )
}

