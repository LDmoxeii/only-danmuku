package edu.only4.danmuku.adapter.portal.api.payload

object VideoLoadPList {

    data class FileItem(
        var fileId: Long,
        var videoId: Long,
        var userId: Long,
        var fileIndex: Int,
        var fileName: String,
        var fileSize: Long,
        var filePath: String,
        var duration: Int
    )
}
