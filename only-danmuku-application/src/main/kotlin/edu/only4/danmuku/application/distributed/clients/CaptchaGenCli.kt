package edu.only4.danmuku.application.distributed.clients

import com.only.engine.enums.CaptchaChannel
import com.only4.cap4k.ddd.core.application.RequestParam

object CaptchaGenCli {

    /**
     * 验证码生成请求
     *
     * @param bizType   业务类型，如 web-auth、login-sms 等
     * @param channel   验证码下发通道，默认 INLINE（图形验证码）；可选 SMS / EMAIL
     * @param targets   当通道为 SMS/EMAIL 时的发送目标（手机号 / 邮箱），INLINE 场景下可为空
     */
    data class Request(
        val bizType: String,
        val channel: CaptchaChannel = CaptchaChannel.INLINE,
        val targets: List<String> = emptyList(),
        val templateCode: String? = null,
    ) : RequestParam<Response>


    data class Response(
        val captchaId: String,
        val byte: String,
        val text: String,
    )
}
