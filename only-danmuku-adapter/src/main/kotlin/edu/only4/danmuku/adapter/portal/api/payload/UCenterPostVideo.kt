package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 发布视频接口载荷
 */
object UCenterPostVideo {

    data class MixedFileItem(
        val uploadId: Long,
        val fileId: Long? = null,
        val fileName: String? = null,
    )

    class Response()
}
