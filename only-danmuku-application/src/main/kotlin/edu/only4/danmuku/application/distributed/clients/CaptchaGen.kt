package edu.only4.danmuku.application.distributed.clients

import com.only4.cap4k.ddd.core.application.RequestParam

object CaptchaGen {

    data class Request(
        val bizType: String,
    ) : RequestParam<Response>


    data class Response(
        val captchaId: String,
        val byte: ByteArray,
        val text: String,
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Response) return false

            if (captchaId != other.captchaId) return false
            if (!byte.contentEquals(other.byte)) return false
            if (text != other.text) return false

            return true
        }

        override fun hashCode(): Int {
            var result = captchaId.hashCode()
            result = 31 * result + byte.contentHashCode()
            result = 31 * result + text.hashCode()
            return result
        }
    }

}
