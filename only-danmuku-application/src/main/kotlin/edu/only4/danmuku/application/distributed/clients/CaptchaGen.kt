package edu.only4.danmuku.application.distributed.clients

import com.only4.cap4k.ddd.core.application.RequestParam

object CaptchaGen {

    data class Request(
        val bizType: String,
    ) : RequestParam<Response>


    data class Response(
        val captchaId: String,
        val byte: String,
        val text: String,
    )

}
