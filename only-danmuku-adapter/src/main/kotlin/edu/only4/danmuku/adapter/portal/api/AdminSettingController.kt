package edu.only4.danmuku.adapter.portal.api

import edu.only4.danmuku.adapter.portal.api.payload.AdminSettingGet
import edu.only4.danmuku.adapter.portal.api.payload.AdminSettingSave
import edu.only4.danmuku.application._share.enums.config.properties.SysSettingProperties
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
class AdminSettingController(
    private val sysSettingProperties: SysSettingProperties,
) {

    /**
     * 获取系统设置信息
     */
    @PostMapping("/getSetting")
    fun adminSettingGet(): AdminSettingGet.Response {
        return AdminSettingGet.Response(
            registerCoinCount = sysSettingProperties.registerCoinCount,
            postVideoCoinCount = sysSettingProperties.postVideoCoinCount,
            videoSize = sysSettingProperties.videoSize,
            videoPCount = sysSettingProperties.videoPCount,
            videoCount = sysSettingProperties.videoCount,
            commentCount = sysSettingProperties.commentCount,
            danmuCount = sysSettingProperties.danmuCount
        )
    }

    /**
     * 保存系统设置信息
     */
    @PostMapping("/saveSetting")
    fun adminSettingSave(@RequestBody @Validated request: AdminSettingSave.Request): AdminSettingSave.Response {
        // TODO: 实现保存系统设置逻辑 (需要持久化到数据库或Redis)
        return AdminSettingSave.Response()
    }

}
