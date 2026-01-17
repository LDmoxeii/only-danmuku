package edu.only4.danmuku.adapter.application.distributed.clients.statistics

import com.only.engine.redis.misc.RedisUtils
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.adapter.portal.api._share.constant.Constants

import edu.only4.danmuku.application.distributed.clients.statistics.ReportVideoPlayOnlineCli

import org.springframework.stereotype.Service
import java.time.Duration

/**
 * 上报视频播放在线人数
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
@Service
class ReportVideoPlayOnlineCliHandler : RequestHandler<ReportVideoPlayOnlineCli.Request, ReportVideoPlayOnlineCli.Response> {
    override fun exec(request: ReportVideoPlayOnlineCli.Request): ReportVideoPlayOnlineCli.Response {
        val userPlayOnlineKey = String.format(
            Constants.REDIS_KEY_VIDEO_PLAY_COUNT_USER,
            request.fileId,
            request.deviceId
        )
        val playOnlineCountKey = String.format(
            Constants.REDIS_KEY_VIDEO_PLAY_COUNT_ONLINE,
            request.fileId
        )

        return ReportVideoPlayOnlineCli.Response(
            if (!RedisUtils.hasKey(userPlayOnlineKey)) {
                // 首次该设备上报：记录设备在线心跳并计数 +1，设置计数 TTL
                RedisUtils.setCacheObject(
                    userPlayOnlineKey,
                    request.fileId,
                    Duration.ofSeconds(Constants.VIDEO_PLAY_ONLINE_DEVICE_TTL_SEC)
                )

                val current = if (RedisUtils.hasKey(playOnlineCountKey)) {
                    RedisUtils.incrAtomicValue(playOnlineCountKey)
                } else {
                    RedisUtils.setAtomicValue(playOnlineCountKey, 1)
                    1
                }
                RedisUtils.expire(playOnlineCountKey, Constants.VIDEO_PLAY_ONLINE_COUNT_TTL_SEC)
                current
            } else {
                // 已存在心跳：仅续期心跳与计数 TTL
                RedisUtils.expire(playOnlineCountKey, Constants.VIDEO_PLAY_ONLINE_COUNT_TTL_SEC)
                RedisUtils.expire(userPlayOnlineKey, Constants.VIDEO_PLAY_ONLINE_DEVICE_TTL_SEC)

                val current = RedisUtils.getAtomicValue(playOnlineCountKey)
                if (current <= 0) 1 else current
            }
        )
    }
}

