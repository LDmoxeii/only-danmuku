package edu.only4.danmuku.adapter.application.distributed.clients.video_search

import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.video_search.SyncVideoBasicsToEsCli

import org.springframework.stereotype.Service

/**
 * 同步视频基础信息到 ES（upsert 文档）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/26
 */
@Service
class SyncVideoBasicsToEsCliHandler : RequestHandler<SyncVideoBasicsToEsCli.Request, SyncVideoBasicsToEsCli.Response> {
    override fun exec(request: SyncVideoBasicsToEsCli.Request): SyncVideoBasicsToEsCli.Response {
        return SyncVideoBasicsToEsCli.Response(
            success = TODO("set success"),
            failReason = TODO("set failReason")
        )
    }
}

