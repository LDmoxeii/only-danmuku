package edu.only4.danmuku.application.distributed.clients

import com.only4.cap4k.ddd.core.application.RequestParam

object CaptchaValidCli {

    data class Request(
        val id: String,
        val input: String,
    ) : RequestParam<Response>

    data class Response(
        val result: Boolean,
    )
}
