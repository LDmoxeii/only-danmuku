package edu.only4.danmuku.application.queries.video_play_history

import com.only4.cap4k.ddd.core.application.query.PageQueryParam

/**
 * 获取用户播放历史
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetUserPlayHistoryQry {

    class Request(
        /** 用户ID */
        val customerId: Long,
    ) : PageQueryParam<HistoryItem>()

    class HistoryItem(
        val historyId: Long,
        val customerId: Long,
        val videoId: Long?,
        val videoName: String?,
        val videoCover: String?,
        val fileIndex: Int,
         val playTime: Long,
    )
}
