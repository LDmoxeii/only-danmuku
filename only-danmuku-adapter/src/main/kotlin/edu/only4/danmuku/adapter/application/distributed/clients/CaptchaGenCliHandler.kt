package edu.only4.danmuku.adapter.application.distributed.clients

import com.only.engine.captcha.CaptchaManager
import com.only.engine.entity.CaptchaContent
import com.only.engine.entity.GenerateCommand
import com.only.engine.entity.GenerateCommand.CharsetPolicy
import com.only.engine.enums.CaptchaCategory
import com.only.engine.enums.CaptchaType
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.CaptchaGenCli
import org.springframework.stereotype.Service

@Service
class CaptchaGenCliHandler(
    private val captchaManager: CaptchaManager,
) : RequestHandler<CaptchaGenCli.Request, CaptchaGenCli.Response> {
    override fun exec(request: CaptchaGenCli.Request): CaptchaGenCli.Response {
        val command = GenerateCommand(
            bizType = request.bizType,
            type = CaptchaType.IMAGE,
            category = CaptchaCategory.LINE,
            charsetPolicy = CharsetPolicy.MATH,
            channel = request.channel,
            length = 1,
            width = 100,
            height = 42,
            targets = request.targets,
            templateCode = request.templateCode,
        )
        val result = captchaManager.generate(command)

        val inlineContent = result.inlineContent
        val (bytes, text) = when (inlineContent) {
            is CaptchaContent.Image -> inlineContent.bytes to inlineContent.text
            is CaptchaContent.Text -> "" to inlineContent.value
            null -> "" to ""
        }

        return CaptchaGenCli.Response(
            captchaId = result.captchaId,
            byte = bytes,
            text = text,
        )

    }
}
