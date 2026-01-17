package edu.only4.danmuku.adapter.application.distributed.clients.system

import com.only.engine.redis.misc.RedisUtils
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.adapter.portal.api._share.constant.Constants
import edu.only4.danmuku.application._share.config.properties.SysSettingProperties

import edu.only4.danmuku.application.distributed.clients.system.SaveSettingsCli

import org.springframework.stereotype.Service

/**
 * 保存系统设置
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
@Service
class SaveSettingsCliHandler(
    private val sysSettingProperties: SysSettingProperties
) : RequestHandler<SaveSettingsCli.Request, SaveSettingsCli.Response> {
    override fun exec(request: SaveSettingsCli.Request): SaveSettingsCli.Response {
        sysSettingProperties.registerCoinCount = request.registerCoinCount
        sysSettingProperties.postVideoCoinCount = request.postVideoCoinCount
        sysSettingProperties.videoSize = request.videoSize
        sysSettingProperties.videoPCount = request.videoPCount
        sysSettingProperties.videoCount = request.videoCount
        sysSettingProperties.commentCount = request.commentCount
        sysSettingProperties.danmukuCount = request.danmukuCount
        sysSettingProperties.renameNicknameCoinCost = request.renameNicknameCoinCost
        RedisUtils.setCacheObject(Constants.REDIS_KEY_SYS_SETTING, sysSettingProperties)
        return SaveSettingsCli.Response()
    }
}

