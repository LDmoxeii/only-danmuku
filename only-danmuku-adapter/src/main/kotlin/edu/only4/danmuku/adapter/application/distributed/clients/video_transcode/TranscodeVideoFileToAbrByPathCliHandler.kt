package edu.only4.danmuku.adapter.application.distributed.clients.video_transcode

import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.video_transcode.TranscodeVideoFileToAbrByPathCli
import edu.only4.danmuku.application.distributed.clients.video_transcode.TranscodeVideoFileToAbrCli

import org.springframework.stereotype.Service

/**
 * 防腐层：按路径转码生成 ABR 输出（不接触数据库ID）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@Service
class TranscodeVideoFileToAbrByPathCliHandler(
    private val delegate: TranscodeVideoFileToAbrCliHandler,
) : RequestHandler<TranscodeVideoFileToAbrByPathCli.Request, TranscodeVideoFileToAbrByPathCli.Response> {
    override fun exec(request: TranscodeVideoFileToAbrByPathCli.Request): TranscodeVideoFileToAbrByPathCli.Response {
        val result = delegate.exec(
            TranscodeVideoFileToAbrCli.Request(
                sourcePath = request.sourcePath,
                outputDir = request.outputDir,
                profiles = request.profiles,
                segmentDurationSec = request.segmentDurationSec
            )
        )
        return TranscodeVideoFileToAbrByPathCli.Response(
            accepted = result.accepted,
            variantsJson = result.variants,
            failReason = result.failReason
        )
    }
}

