package edu.only4.danmuku.adapter.portal.api.payload

object AccountCheckCode {

    data class Response(
        val checkCodeKey: String,
        val checkCode: String,
    )
}
