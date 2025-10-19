package edu.only4.danmuku.application.queries.customer_profile

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取用户信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetCustomerProfileQry {

    data class Request(
        val customerId: Long,
    ) : RequestParam<Response>

    data class Response(
        val customerId: Long,
        val nickName: String,
        val avatar: String?,
        val sex: Byte,
        val birthday: String?,
        val school: String?,
        val personIntroduction: String?,
        val noticeInfo: String?,
        val theme: Byte,
        val currentCoinCount: Int,
        val fansCount: Int = 0,
        val focusCount: Int = 0,
    )
}
