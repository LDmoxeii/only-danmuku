package edu.only4.danmuku.adapter.portal.api.payload.file

object DeleteUploadSession {
    data class Request(
        val uploadId: Long,
    )
}
