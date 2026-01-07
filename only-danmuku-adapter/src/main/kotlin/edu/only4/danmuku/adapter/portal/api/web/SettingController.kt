package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.redis.misc.RedisUtils
import edu.only4.danmuku.adapter.portal.api._share.constant.Constants
import edu.only4.danmuku.adapter.portal.api.payload.setting.GetSetting
import edu.only4.danmuku.application._share.config.properties.SysSettingProperties
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SaIgnore
@RestController
@RequestMapping("/sysSetting")
class SettingController(
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
}
