package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 发布视频接口载荷
 */
object UCenterPostVideo {

    data class MixedFileItem(
        val uploadId: Long,
        val fileName: String,
        val fileIndex: Int? = null,
        val fileSize: Long? = null,
        val duration: Int? = null,
    )

    class Response()
}
