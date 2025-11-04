package edu.only4.danmuku.application.queries.customer_profile

import com.only4.cap4k.ddd.core.application.RequestParam
import edu.only4.danmuku.domain.aggregates.customer_profile.enums.ThemeType

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
        val sex: Int,
        val birthday: String?,
        val school: String?,
        val personIntroduction: String?,
        val noticeInfo: String?,
        val theme: ThemeType,
        val currentCoinCount: Int,
        val fansCount: Long = 0,
        val focusCount: Long = 0,
    )
}
