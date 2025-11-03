package edu.only4.danmuku.application.queries.customer_video_series

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取用户视频系列及关联视频
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetCustomerVideoSeriesVideoQry {

    data class Request(
        /** 用户ID */
        val userId: Long,
    ) : RequestParam<List<Response>>

    data class Response(
        /** 系列ID */
        val seriesId: Long,
        /** 系列名称 */
        val seriesName: String? = null,
        /** 系列描述 */
        val seriesDescription: String? = null,
        /** 系列排序 */
        val sort: Int? = null,
        /** 视频列表 */
        val videoList: List<VideoItem>? = null,
    )

    data class VideoItem(
        val videoId: Long,
        val videoCover: String,
        val videoName: String,
        val playCount: Int,
        val sort: Int,
    )
}
