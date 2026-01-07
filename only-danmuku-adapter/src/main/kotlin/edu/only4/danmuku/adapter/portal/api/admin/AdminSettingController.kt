package edu.only4.danmuku.adapter.portal.api.admin

import com.only.engine.redis.misc.RedisUtils
import edu.only4.danmuku.adapter.portal.api._share.constant.Constants
import edu.only4.danmuku.adapter.portal.api.payload.admin_setting.GetSetting
import edu.only4.danmuku.adapter.portal.api.payload.admin_setting.SaveSetting
import edu.only4.danmuku.application._share.config.properties.SysSettingProperties
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/setting")
@Validated
class AdminSettingController(
    private val sysSettingProperties: SysSettingProperties,
) {

    @PostMapping("/getSetting")
    fun getSetting(): GetSetting.Response {
        val properties = RedisUtils.getCacheObject<SysSettingProperties>(Constants.REDIS_KEY_SYS_SETTING)
            ?: sysSettingProperties.also {
                RedisUtils.setCacheObject(Constants.REDIS_KEY_SYS_SETTING, it)
            }

        return GetSetting.Converter.INSTANCE.fromApp(properties)
    }

    @PostMapping("/saveSetting")
    fun saveSetting(@RequestBody @Validated request: SaveSetting.Request) {
        val properties = SaveSetting.Converter.INSTANCE.toApp(request)

        RedisUtils.setCacheObject(Constants.REDIS_KEY_SYS_SETTING, properties)
    }

}
