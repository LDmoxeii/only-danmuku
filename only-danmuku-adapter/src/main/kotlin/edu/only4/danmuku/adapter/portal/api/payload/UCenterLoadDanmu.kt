package edu.only4.danmuku.adapter.portal.api.payload

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.PageParam

/**
 * 加载用户收到的弹幕接口载荷
 */
object UCenterLoadDanmu {

    data class Request(
        val videoId: String? = null
    ) : PageParam()

    /**
     * 弹幕项
     */
    data class DanmukuItem(
        var danmukuId: String? = null,
        var videoId: String? = null,
        var videoName: String? = null,
        var text: String? = null,
        var userId: String? = null,
        var mode: Int? = null,
        var color: String? = null,
        var time: Int? = null,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var postTime: Long,
    )
}
