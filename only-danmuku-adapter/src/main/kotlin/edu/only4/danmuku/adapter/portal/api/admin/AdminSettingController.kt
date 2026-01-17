package edu.only4.danmuku.adapter.portal.api.admin

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.admin_setting.GetSetting
import edu.only4.danmuku.adapter.portal.api.payload.admin_setting.SaveSetting
import edu.only4.danmuku.application.distributed.clients.system.GetSettingsCli
import edu.only4.danmuku.application.distributed.clients.system.SaveSettingsCli
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/setting")
@Validated
class AdminSettingController {

    @PostMapping("/getSetting")
    fun getSetting(): GetSetting.Response {
        val properties = Mediator.requests.send(
            GetSettingsCli.Request()
        )

        return GetSetting.Converter.INSTANCE.fromCli(properties)
    }

    @PostMapping("/saveSetting")
    fun saveSetting(@RequestBody @Validated request: SaveSetting.Request) {
        Mediator.requests.send(SaveSetting.Converter.INSTANCE.toCli(request))
    }

}
