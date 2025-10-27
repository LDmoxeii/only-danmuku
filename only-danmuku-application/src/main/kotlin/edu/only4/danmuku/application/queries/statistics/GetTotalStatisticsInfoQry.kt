package edu.only4.danmuku.application.queries.statistics

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取总统计数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/18
 */
object GetTotalStatisticsInfoQry {

    class Request(
        /** 用户ID - 可选，不传则查询全局统计 */
        val userId: Long? = null,
    ) : RequestParam<Response>

    /**
     * 总统计数据响应
     *
     * 对应 easylive 的 totalCountInfo 格式，按字段名分类
     */
    data class Response(
        /** 用户数 */
        val userCount: Int = 0,
        /** 播放量 */
        val playCount: Int = 0,
        /** 评论数 */
        val commentCount: Int = 0,
        /** 弹幕数 */
        val danmuCount: Int = 0,
        /** 点赞数 */
        val likeCount: Int = 0,
        /** 收藏数 */
        val collectCount: Int = 0,
        /** 投币数 */
        val coinCount: Int = 0,
    )
}
