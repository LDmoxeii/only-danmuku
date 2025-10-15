package edu.only4.danmuku.adapter.portal.api._share.configure

import com.only.engine.captcha.CaptchaManager
import com.only.engine.entity.GenerateCommand
import com.only.engine.entity.GenerateResult
import com.only.engine.enums.CaptchaChannel
import com.only.engine.enums.CaptchaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CaptchaController(
    private val captchaManager: CaptchaManager,
) {

    /**
     * 生成验证码
     */
    @PostMapping("/auth/code")
    fun test(@RequestBody command: String): GenerateResult {
        val cmd = GenerateCommand(
            bizType = command,
            type = CaptchaType.IMAGE,
            channel = CaptchaChannel.INLINE
        )
        return captchaManager.generate(cmd)
    }
}
