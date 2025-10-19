package edu.only4.danmuku.adapter.application.distributed.clients

import com.only.engine.captcha.CaptchaManager
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.CaptchaValid
import org.springframework.stereotype.Service

@Service
class CaptchaValidClientHandler(
    private val captchaManager: CaptchaManager,
) : RequestHandler<CaptchaValid.Request, CaptchaValid.Response> {
    override fun exec(request: CaptchaValid.Request): CaptchaValid.Response {
        val result = captchaManager.verify(request.id, request.input)
        return CaptchaValid.Response(
            result
        )
    }


}
