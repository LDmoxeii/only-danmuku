package edu.only4.danmuku.application.queries.video

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 查询成品视频文件的清晰度档位列表
 */
object ListVideoFileVariantsQry {

    data class Request(
        val fileId: Long,
    ) : ListQueryParam<Response>

    data class Response(
        val qualities: List<String>,
        val variantJson: String
    )
}
