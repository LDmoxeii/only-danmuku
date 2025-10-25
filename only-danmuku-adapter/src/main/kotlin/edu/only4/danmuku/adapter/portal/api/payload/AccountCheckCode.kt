package edu.only4.danmuku.adapter.portal.api.payload

object AccountCheckCode {

    data class Response(
        val captchaId: String,
        val byte: String,
    )
}
