package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.redis.misc.RedisUtils
import edu.only4.danmuku.adapter.portal.api._share.constant.Constants
import edu.only4.danmuku.adapter.portal.api.payload.AdminSettingGet
import edu.only4.danmuku.application._share.config.properties.SysSettingProperties
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SaIgnore
@RestController
@RequestMapping("/sysSetting")
@Validated
class CompatibleSettingController(
    private val sysSettingProperties: SysSettingProperties,
) {

    @PostMapping("/getSetting")
    fun getSetting(): AdminSettingGet.Response {
        val properties = RedisUtils.getCacheObject<SysSettingProperties>(Constants.REDIS_KEY_SYS_SETTING)
            ?: sysSettingProperties.also {
                RedisUtils.setCacheObject(Constants.REDIS_KEY_SYS_SETTING, it)
            }

        return AdminSettingGet.Response(
            registerCoinCount = properties.registerCoinCount,
            postVideoCoinCount = properties.postVideoCoinCount,
            videoSize = properties.videoSize,
            videoPCount = properties.videoPCount,
            videoCount = properties.videoCount,
            commentCount = properties.commentCount,
            danmuCount = properties.danmuCount
        )
    }
}
