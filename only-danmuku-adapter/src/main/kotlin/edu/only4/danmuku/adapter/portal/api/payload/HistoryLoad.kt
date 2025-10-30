package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam
import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation

/**
 * 加载播放历史接口载荷
 */
object HistoryLoad {

    class Request(
        /** 其他过滤条件可以在这里添加 */
    ) : PageParam()

    /**
     * 播放历史项
     */
    data class Response(
        /** 播放历史ID */
        var historyId: String? = null,
        /** 视频ID */
        var videoId: String? = null,
        /** 视频名称 */
        var videoName: String? = null,
        /** 视频封面 */
        var videoCover: String? = null,
        /** 文件索引 */
        var fileIndex: Int? = null,
        /** 播放时间 */
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss" )
        var playTime: Long? = null,
    )
}


