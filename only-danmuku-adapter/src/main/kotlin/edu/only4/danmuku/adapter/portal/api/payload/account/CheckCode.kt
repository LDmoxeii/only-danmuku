package edu.only4.danmuku.adapter.portal.api.payload.account

object CheckCode {

    data class Response(
        val checkCodeKey: String,
        val checkCode: String,
    )
}
