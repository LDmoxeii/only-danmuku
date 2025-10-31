package edu.only4.danmuku.application.queries.customer_video_series

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取用户视频系列
 *
 * 该文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 并按前端需要补充了 cover 与 updateTime 字段
 */
object GetCustomerVideoSeriesListQry {

    data class Request(
        /** 用户ID */
        val userId: Long
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
        /** 视频数量 */
        val videoCount: Int? = 0,
        /** 列表封面(取第一个视频的cover) */
        val cover: String? = null,
        /** 创建时间(秒) */
        val createTime: Long,
        /** 更新时间(秒，前端展示用；暂用与创建时间一致或由处理器计算) */
        val updateTime: Long? = null,
    )
}

