package edu.only4.danmuku.adapter.application.distributed.clients

import com.only.engine.captcha.CaptchaManager
import com.only.engine.entity.CaptchaContent
import com.only.engine.entity.GenerateCommand
import com.only.engine.entity.GenerateCommand.CharsetPolicy
import com.only.engine.enums.CaptchaCategory
import com.only.engine.enums.CaptchaChannel
import com.only.engine.enums.CaptchaType
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.CaptchaGen
import org.springframework.stereotype.Service

@Service
class CaptchaGenClientHandler(
    private val captchaManager: CaptchaManager,
) : RequestHandler<CaptchaGen.Request, CaptchaGen.Response> {
    override fun exec(request: CaptchaGen.Request): CaptchaGen.Response {
        val command = GenerateCommand(
            bizType = request.bizType,
            type = CaptchaType.IMAGE,
            category = CaptchaCategory.LINE,
            charsetPolicy = CharsetPolicy.MATH,
            channel = CaptchaChannel.INLINE,
            length = 3,
            width = 100,
            height = 42
        )
        val result = captchaManager.generate(command)

        @Suppress("CAST_NEVER_SUCCEEDS")
        return CaptchaGen.Response(
            result.captchaId,
            (result.inlineContent as CaptchaContent.Image).bytes,
            (result.inlineContent as CaptchaContent.Image).text,
        )

    }
}
