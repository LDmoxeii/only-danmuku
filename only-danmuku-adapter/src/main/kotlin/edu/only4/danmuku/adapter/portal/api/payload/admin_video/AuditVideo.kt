package edu.only4.danmuku.adapter.portal.api.payload.admin_video

object AuditVideo {

    data class Request(
        val videoId: Long,
        val status: Int,
        val reason: String?
    )
}
