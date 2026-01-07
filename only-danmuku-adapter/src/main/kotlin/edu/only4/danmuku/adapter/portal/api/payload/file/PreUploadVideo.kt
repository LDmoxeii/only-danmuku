package edu.only4.danmuku.adapter.portal.api.payload.file

object PreUploadVideo {
    data class Request(
        val fileName: String,
        val chunks: Int,
    )
}
