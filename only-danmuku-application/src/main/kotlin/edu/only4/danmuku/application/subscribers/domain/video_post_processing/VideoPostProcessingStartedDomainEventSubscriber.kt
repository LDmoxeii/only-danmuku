package edu.only4.danmuku.application.subscribers.domain.video_post_processing

import com.only.engine.json.misc.JsonUtils
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_post_processing.ApplyVideoPostProcessingTranscodeResultCmd
import edu.only4.danmuku.application.distributed.clients.video_transcode.GenerateVideoAbrMasterCli
import edu.only4.danmuku.application.distributed.clients.video_transcode.MergeUploadToMp4ByPathCli
import edu.only4.danmuku.application.distributed.clients.video_transcode.TranscodeVideoFileToAbrByPathCli
import edu.only4.danmuku.application.distributed.clients.video_transcode.UploadVideoAbrOutputCli
import edu.only4.danmuku.application.queries.video_transcode.GetUploadSessionTempPathQry
import edu.only4.danmuku.domain.aggregates.video_post_processing.events.VideoPostProcessingStartedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 处理聚合已初始化事件，携带 fileList(uploadId,fileIndex,outputDir,objectPrefix,encOutputDir)
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@Service
class VideoPostProcessingStartedDomainEventSubscriber {

    @EventListener(VideoPostProcessingStartedDomainEvent::class)
    fun on(event: VideoPostProcessingStartedDomainEvent) {
        val profilesJson = defaultProfilesJson()
        event.fileList.forEach { file ->
            val tempPath = Mediator.queries.send(
                GetUploadSessionTempPathQry.Request(uploadId = file.uploadId)
            ).tempPath

            val merged = Mediator.requests.send(
                MergeUploadToMp4ByPathCli.Request(
                    tempPath = tempPath,
                    outputDir = file.outputDir
                )
            )
            if (!merged.success) {
                Mediator.commands.send(
                    ApplyVideoPostProcessingTranscodeResultCmd.Request(
                        videoPostId = event.videoPostId,
                        fileIndex = file.fileIndex,
                        success = false,
                        outputPrefix = null,
                        outputPath = file.outputDir,
                        duration = merged.duration,
                        fileSize = merged.fileSize,
                        variantsJson = null,
                        failReason = merged.failReason
                    )
                )
                return@forEach
            }

            val transcode = Mediator.requests.send(
                TranscodeVideoFileToAbrByPathCli.Request(
                    sourcePath = merged.mergedMp4Path,
                    outputDir = file.outputDir,
                    profiles = profilesJson,
                    segmentDurationSec = 6,
                )
            )
            if (!transcode.accepted) {
                Mediator.commands.send(
                    ApplyVideoPostProcessingTranscodeResultCmd.Request(
                        videoPostId = event.videoPostId,
                        fileIndex = file.fileIndex,
                        success = false,
                        outputPrefix = null,
                        outputPath = file.outputDir,
                        duration = merged.duration,
                        fileSize = merged.fileSize,
                        variantsJson = null,
                        failReason = transcode.failReason
                    )
                )
                return@forEach
            }

            val uploaded = Mediator.requests.send(
                UploadVideoAbrOutputCli.Request(
                    outputDir = file.outputDir,
                    objectPrefix = file.objectPrefix
                )
            )
            if (!uploaded.success) {
                Mediator.commands.send(
                    ApplyVideoPostProcessingTranscodeResultCmd.Request(
                        videoPostId = event.videoPostId,
                        fileIndex = file.fileIndex,
                        success = false,
                        outputPrefix = null,
                        outputPath = file.outputDir,
                        duration = merged.duration,
                        fileSize = merged.fileSize,
                        variantsJson = null,
                        failReason = uploaded.failReason
                    )
                )
                return@forEach
            }

            val outputPrefix = uploaded.storagePrefix ?: file.objectPrefix
            val master = Mediator.requests.send(
                GenerateVideoAbrMasterCli.Request(
                    outputPrefix = outputPrefix,
                    variantsJson = transcode.variantsJson
                )
            )
            val success = master.success
            Mediator.commands.send(
                ApplyVideoPostProcessingTranscodeResultCmd.Request(
                    videoPostId = event.videoPostId,
                    fileIndex = file.fileIndex,
                    success = success,
                    outputPrefix = if (success) outputPrefix else null,
                    outputPath = file.outputDir,
                    duration = merged.duration,
                    fileSize = merged.fileSize,
                    variantsJson = if (success) transcode.variantsJson else null,
                    failReason = if (success) null else master.failReason
                )
            )
        }
    }

    private fun defaultProfilesJson(): String {
        val profiles = listOf(
            AbrProfile("1080p", 1920, 1080, 5000, 128),
            AbrProfile("720p", 1280, 720, 2800, 128),
            AbrProfile("480p", 854, 480, 1400, 96),
            AbrProfile("360p", 640, 360, 800, 64),
        )
        return JsonUtils.toJsonString(profiles) ?: "[]"
    }

    data class AbrProfile(
        val quality: String,
        val width: Int,
        val height: Int,
        val videoBitrateKbps: Int,
        val audioBitrateKbps: Int,
    )
}
