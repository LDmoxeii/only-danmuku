package edu.only4.danmuku.adapter.application.distributed.clients.video_transcode

import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.video_transcode.ProbeVideoResolutionCli

import org.springframework.stereotype.Service

/**
 * 防腐层：使用 ffprobe 获取源视频分辨率，为 ABR 档位决策提供输入
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class ProbeVideoResolutionCliHandler : RequestHandler<ProbeVideoResolutionCli.Request, ProbeVideoResolutionCli.Response> {
    override fun exec(request: ProbeVideoResolutionCli.Request): ProbeVideoResolutionCli.Response {
        return ProbeVideoResolutionCli.Response(
            width = TODO("set width"),
            height = TODO("set height"),
            bitrateKbps = TODO("set bitrateKbps")
        )
    }
}

