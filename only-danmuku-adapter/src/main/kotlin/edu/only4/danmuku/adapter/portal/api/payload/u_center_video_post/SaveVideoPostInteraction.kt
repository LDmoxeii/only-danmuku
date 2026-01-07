package edu.only4.danmuku.adapter.portal.api.payload.u_center_video_post

object SaveVideoPostInteraction {

    data class Request(
        val videoPostId: Long,
        val interaction: String,
    )
}
