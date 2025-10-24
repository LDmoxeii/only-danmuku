package edu.only4.danmuku.application.queries.video_danmuku

import com.only4.cap4k.ddd.core.application.RequestParam

object CheckDanmukuExistsQry {
    data class Request(
        val danmukuId: Long
    ) : RequestParam<Response>

    data class Response(
        val exists: Boolean
    )
}

