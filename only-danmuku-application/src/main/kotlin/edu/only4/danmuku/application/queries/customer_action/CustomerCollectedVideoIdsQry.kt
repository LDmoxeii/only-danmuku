package edu.only4.danmuku.application.queries.customer_action

import com.only4.cap4k.ddd.core.application.query.PageQueryParam

/**
 * 用户收藏的视频ID列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object CustomerCollectedVideoIdsQry {

    data class Request(
        val customerId: Long,
    ) : PageQueryParam<Response>()

    data class Response(
        val videoId: Long,
    )
}
