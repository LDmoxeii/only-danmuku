package edu.only4.danmuku.adapter.portal.api.compatible

import com.only.engine.redis.misc.RedisUtils
import edu.only4.danmuku.adapter.portal.api._share.constant.Constants
import edu.only4.danmuku.adapter.portal.api.payload.AdminSettingGet
import edu.only4.danmuku.adapter.portal.api.payload.AdminSettingSave
import edu.only4.danmuku.application._share.config.properties.SysSettingProperties
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员系统设置控制器
 */
@RestController
@RequestMapping("/admin/setting")
@Validated
class CompatibleAdminSettingController(
    private val sysSettingProperties: SysSettingProperties,
) {

    /**
     * 获取系统设置信息
     */
    @PostMapping("/getSetting")
    fun adminSettingGet(): AdminSettingGet.Response {
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

    /**
     * 保存系统设置信息
     */
    @PostMapping("/saveSetting")
    fun adminSettingSave(@RequestBody @Validated request: AdminSettingSave.Request): AdminSettingSave.Response {
        val properties = SysSettingProperties().apply {
            registerCoinCount = request.registerCoinCount
            postVideoCoinCount = request.postVideoCoinCount
            videoSize = request.videoSize
            videoPCount = request.videoPCount
            videoCount = request.videoCount
            commentCount = request.commentCount
            danmuCount = request.danmuCount
        }

        RedisUtils.setCacheObject(Constants.REDIS_KEY_SYS_SETTING, properties)
        return AdminSettingSave.Response()
    }

}
