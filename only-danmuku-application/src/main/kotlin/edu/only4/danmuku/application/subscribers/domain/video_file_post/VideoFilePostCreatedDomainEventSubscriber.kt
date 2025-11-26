package edu.only4.danmuku.application.subscribers.domain.video_file_post

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_file_post.UpdateVideoFilePostTranscodeResultCmd
import edu.only4.danmuku.application.distributed.clients.video_transcode.CleanupMergedMp4Cli
import edu.only4.danmuku.application.distributed.clients.video_transcode.MergeUploadToMp4Cli
import edu.only4.danmuku.application.distributed.clients.video_transcode.ProbeVideoResolutionCli
import edu.only4.danmuku.application.distributed.clients.video_transcode.TranscodeVideoFileToAbrCli
import edu.only4.danmuku.application.queries.video_transcode.GetUploadSessionTempPathQry
import edu.only4.danmuku.domain.aggregates.video_file_post.events.VideoFilePostCreatedDomainEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 文件分P创建完成事件，驱动转码 CLI
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Service
class VideoFilePostCreatedDomainEventSubscriber {

    private val logger = LoggerFactory.getLogger(javaClass)

    @EventListener(VideoFilePostCreatedDomainEvent::class)
    fun on(event: VideoFilePostCreatedDomainEvent) {
        val file = event.entity
        val tempPathUsed = Mediator.queries.send(
            GetUploadSessionTempPathQry.Request(uploadId = file.uploadId)
        ).tempPath
        var mergeResult: MergeUploadToMp4Cli.Response? = null
        runCatching {
            mergeResult = Mediator.requests.send(
                MergeUploadToMp4Cli.Request(
                    uploadId = file.uploadId,
                    customerId = file.customerId,
                    videoId = file.videoId,
                    fileIndex = file.fileIndex,
                    tempPath = tempPathUsed
                )
            )
            if (!mergeResult.success) throw KnownException(mergeResult.failReason ?: "合并分片失败")

            val probe = Mediator.requests.send(
                ProbeVideoResolutionCli.Request(
                    inputPath = mergeResult.mergedMp4Path
                )
            )

            val profilesJson = buildDefaultProfiles()
            val transcodeResult = Mediator.requests.send(
                TranscodeVideoFileToAbrCli.Request(
                    sourcePath = mergeResult.mergedMp4Path,
                    outputDir = mergeResult.outputDir,
                    profiles = profilesJson,
                    segmentDurationSec = 6,
                )
            )

            Mediator.commands.send(
                UpdateVideoFilePostTranscodeResultCmd.Request(
                    videoFilePostId = file.id,
                    success = transcodeResult.accepted,
                    outputPath = mergeResult.outputDir,
                    sourceWidth = probe.width,
                    sourceHeight = probe.height,
                    sourceBitrateKbps = probe.bitrateKbps,
                    variants = transcodeResult.variants,
                    duration = mergeResult.duration,
                    fileSize = mergeResult.fileSize,
                    failReason = transcodeResult.failReason
                )
            )
        }.onFailure { ex ->
            logger.error("ABR 转码触发失败", ex)
            Mediator.commands.send(
                UpdateVideoFilePostTranscodeResultCmd.Request(
                    videoFilePostId = file.id,
                    success = false,
                    failReason = ex.message
                )
            )
        }.also {
            mergeResult?.mergedMp4Path?.let { mp4 ->
                runCatching { Mediator.requests.send(CleanupMergedMp4Cli.Request(mergedMp4Path = mp4)) }
                    .onFailure { logger.warn("清理临时 MP4 失败: {}", mp4, it) }
            }
//            tempPathUsed.let { tmp ->
//                runCatching { Mediator.requests.send(CleanupTempUploadDirCli.Request(tempPath = tmp)) }
//                    .onFailure { logger.warn("清理临时目录失败: {}", tmp, it) }
//            }
        }
    }

    // 默认档位，后续可从配置中心读取
    private fun buildDefaultProfiles(): String {
        return """
            [
              {"quality":"1080p","width":1920,"height":1080,"videoBitrateKbps":5000,"audioBitrateKbps":128},
              {"quality":"720p","width":1280,"height":720,"videoBitrateKbps":2800,"audioBitrateKbps":128},
              {"quality":"480p","width":854,"height":480,"videoBitrateKbps":1400,"audioBitrateKbps":96},
              {"quality":"360p","width":640,"height":360,"videoBitrateKbps":800,"audioBitrateKbps":64}
            ]
        """.trimIndent()
    }
}
