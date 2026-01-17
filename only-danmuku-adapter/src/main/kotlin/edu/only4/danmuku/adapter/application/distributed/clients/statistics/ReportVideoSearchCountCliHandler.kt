package edu.only4.danmuku.adapter.application.distributed.clients.statistics

import com.only.engine.redis.misc.RedisUtils
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.adapter.portal.api._share.constant.Constants

import edu.only4.danmuku.application.distributed.clients.statistics.ReportVideoSearchCountCli

import org.springframework.stereotype.Service

/**
 * 上报视频搜索次数
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
@Service
class ReportVideoSearchCountCliHandler : RequestHandler<ReportVideoSearchCountCli.Request, ReportVideoSearchCountCli.Response> {
    override fun exec(request: ReportVideoSearchCountCli.Request): ReportVideoSearchCountCli.Response {
        RedisUtils.incrZSetScore(Constants.REDIS_KEY_VIDEO_SEARCH_COUNT, request.keyword, 1.0)
        return ReportVideoSearchCountCli.Response(
        )
    }
}

