package edu.only4.danmuku.adapter.portal.api.payload.video_encrypt

object AdminRotateEncKey {
    data class Request(
        val filePostId: Long,
        val reason: String? = null,
    )

    data class Response(
        val newKeyVersion: Int,
        val failReason: String?,
    )
}
