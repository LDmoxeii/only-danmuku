package edu.only4.danmuku.adapter.application.distributed.clients.video_transcode

import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.video_transcode.TranscodeVideoFileToAbrCli

import org.springframework.stereotype.Service

/**
 * 防腐层：调用 FFmpeg/脚本生成多分辨率 HLS 与 master.m3u8
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class TranscodeVideoFileToAbrCliHandler : RequestHandler<TranscodeVideoFileToAbrCli.Request, TranscodeVideoFileToAbrCli.Response> {
    override fun exec(request: TranscodeVideoFileToAbrCli.Request): TranscodeVideoFileToAbrCli.Response {
        return TranscodeVideoFileToAbrCli.Response(
            accepted = TODO("set accepted"),
            variants = TODO("set variants"),
            failReason = TODO("set failReason")
        )
    }
}

