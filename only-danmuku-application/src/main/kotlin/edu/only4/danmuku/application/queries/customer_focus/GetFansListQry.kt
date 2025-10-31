package edu.only4.danmuku.application.queries.customer_focus

import com.only4.cap4k.ddd.core.application.query.PageQueryParam

/**
 * 获取粉丝列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetFansListQry {

    data class Request(
        val userId: Long,
    ) : PageQueryParam<Response>()

    data class Response(
        val userId: Long,
        val nickName: String,
        val avatar: String?,
        val personIntroduction: String? = null,
        val fansCount: Int = 0,
        val haveFocus: Boolean = false,
        /** 互关 1，是；0，否 */
        val focusType: Int = 0,
    )
}
