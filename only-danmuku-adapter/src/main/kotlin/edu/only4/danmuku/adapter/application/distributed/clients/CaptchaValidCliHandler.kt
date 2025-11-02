package edu.only4.danmuku.adapter.application.distributed.clients

import com.only.engine.captcha.CaptchaManager
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.CaptchaValidCli
import org.springframework.stereotype.Service

@Service
class CaptchaValidCliHandler(
    private val captchaManager: CaptchaManager,
) : RequestHandler<CaptchaValidCli.Request, CaptchaValidCli.Response> {
    override fun exec(request: CaptchaValidCli.Request): CaptchaValidCli.Response {
        val result = captchaManager.verify(request.id, request.input)
        return CaptchaValidCli.Response(
            result
        )
    }


}
