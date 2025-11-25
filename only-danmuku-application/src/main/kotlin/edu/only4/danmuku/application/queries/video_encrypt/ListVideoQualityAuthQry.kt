package edu.only4.danmuku.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 查询清晰度授权策略列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object ListVideoQualityAuthQry {

    data class Request(
        val fileId: Long
    ) : ListQueryParam<Response>

    data class Response(
        val policiesJson: String
    )
}
