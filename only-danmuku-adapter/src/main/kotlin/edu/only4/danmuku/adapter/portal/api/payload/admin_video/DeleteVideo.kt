package edu.only4.danmuku.adapter.portal.api.payload.admin_video

object DeleteVideo {

    data class Request(
        val videoId: Long,
    )
}
