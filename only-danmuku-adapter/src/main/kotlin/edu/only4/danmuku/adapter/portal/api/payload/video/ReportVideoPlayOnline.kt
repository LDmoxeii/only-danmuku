package edu.only4.danmuku.adapter.portal.api.payload.video

object ReportVideoPlayOnline {

    data class Request(
        val fileId: Long,
        val deviceId: String,
    )
}
