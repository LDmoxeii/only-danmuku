package edu.only4.danmuku.application.queries.statistics

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取前一天的统计数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetPreviousDayStatisticsInfoQry {

    data class Request(
        /** 用户ID - 可选，不传则查询全局统计 */
        val userId: Long? = null,
    ) : RequestParam<Response>

    /**
     * 前一天统计数据响应
     *
     * 对应 easylive 的 preDayData 格式，按统计类型分类
     */
    data class Response(
        /** 用户数（对应 FANS 类型） */
        val userCount: Int = 0,
        /** 播放量（对应 PLAY 类型） */
        val playCount: Int = 0,
        /** 评论数（对应 COMMENT 类型） */
        val commentCount: Int = 0,
        /** 弹幕数（对应 DANMU 类型） */
        val danmuCount: Int = 0,
        /** 点赞数（对应 LIKE 类型） */
        val likeCount: Int = 0,
        /** 收藏数（对应 COLLECTION 类型） */
        val collectCount: Int = 0,
        /** 投币数（对应 COIN 类型） */
        val coinCount: Int = 0,
    )
}
