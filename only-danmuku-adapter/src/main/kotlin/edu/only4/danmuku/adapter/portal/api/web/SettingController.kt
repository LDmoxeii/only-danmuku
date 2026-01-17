package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.setting.GetSetting
import edu.only4.danmuku.application.distributed.clients.system.GetSettingsCli
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SaIgnore
@RestController
@RequestMapping("/sysSetting")
class SettingController {

    @PostMapping("/getSetting")
    fun getSetting(): GetSetting.Response {
        val properties = Mediator.requests.send(
            GetSettingsCli.Request()
        )

        return GetSetting.Converter.INSTANCE.fromCli(properties)
    }
}
