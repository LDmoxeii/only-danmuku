package edu.only4.danmuku.adapter.application.distributed.clients.system

import com.only.engine.redis.misc.RedisUtils
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.adapter.portal.api._share.constant.Constants
import edu.only4.danmuku.application._share.config.properties.SysSettingProperties

import edu.only4.danmuku.application.distributed.clients.system.GetSettingsCli

import org.springframework.stereotype.Service

/**
 * 获取系统设置
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
@Service
class GetSettingsCliHandler(
    private val sysSettingProperties: SysSettingProperties
) : RequestHandler<GetSettingsCli.Request, GetSettingsCli.Response> {
    override fun exec(request: GetSettingsCli.Request): GetSettingsCli.Response {
        val properties = RedisUtils.getCacheObject<SysSettingProperties>(Constants.REDIS_KEY_SYS_SETTING)
            ?: sysSettingProperties.also {
                RedisUtils.setCacheObject(Constants.REDIS_KEY_SYS_SETTING, it)
            }
        return GetSettingsCli.Response(
            registerCoinCount = properties.registerCoinCount,
            postVideoCoinCount = properties.postVideoCoinCount,
            videoSize = properties.videoSize,
            videoPCount = properties.videoPCount,
            videoCount = properties.videoCount,
            commentCount = properties.commentCount,
            danmukuCount = properties.danmukuCount,
            renameNicknameCoinCost = properties.renameNicknameCoinCost
        )
    }
}

