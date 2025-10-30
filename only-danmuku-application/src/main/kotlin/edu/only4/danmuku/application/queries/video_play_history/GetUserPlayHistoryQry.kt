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
        /** 播放历史ID */
        val historyId: Long,
        /** 用户ID */
        val customerId: Long,
        /** 视频ID */
        val videoId: Long,
        /** 视频名称 */
        val videoName: String,
        /** 视频封面 */
        val videoCover: String?,
        /** 文件索引 */
        val fileIndex: Int,
        /** 播放时间 */
        val playTime: Long,
    )
}
