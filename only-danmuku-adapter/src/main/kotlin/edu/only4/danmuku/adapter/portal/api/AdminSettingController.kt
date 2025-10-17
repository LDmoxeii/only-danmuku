package edu.only4.danmuku.adapter.portal.api

import edu.only4.danmuku.adapter.portal.api.payload.AdminSettingGet
import edu.only4.danmuku.adapter.portal.api.payload.AdminSettingSave
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
class AdminSettingController {

    /**
     * 获取系统设置
     */
    @PostMapping("/getSetting")
    fun adminSettingGet(): AdminSettingGet.Response {
        // TODO: 实现获取系统设置逻辑
        return AdminSettingGet.Response()
    }

    /**
     * 保存系统设置
     */
    @PostMapping("/saveSetting")
    fun adminSettingSave(@RequestBody @Validated request: AdminSettingSave.Request): AdminSettingSave.Response {
        // TODO: 实现保存系统设置逻辑
        return AdminSettingSave.Response()
    }

}
