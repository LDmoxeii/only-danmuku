package edu.only4.danmuku.application.subscribers.domain.video_file_post

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.domain.aggregates.video_file_post.events.VideoFilePostCreatedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import edu.only4.danmuku.application.commands.video_file_post.UpdateVideoFilePostTranscodeResultCmd
import edu.only4.danmuku.application.distributed.clients.video_transcode.ProbeVideoResolutionCli
import edu.only4.danmuku.application.distributed.clients.video_transcode.TranscodeVideoFileToAbrCli
import edu.only4.danmuku.application._share.config.properties.FileAppProperties
import edu.only4.danmuku.application._share.constants.Constants
import com.only.engine.exception.KnownException
import java.io.File

/**
 * 文件分P创建完成事件，驱动转码 CLI
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Service
class VideoFilePostCreatedDomainEventSubscriber(
    private val fileProps: FileAppProperties,
) {

    @EventListener(VideoFilePostCreatedDomainEvent::class)
    fun on(event: VideoFilePostCreatedDomainEvent) {
        // TODO 事件发出时，还没有文件路径
        val file = event.entity
        val absolutePath = resolveSourcePath(file.filePath)

        val probe = Mediator.requests.send(
            ProbeVideoResolutionCli.Request(
                inputPath = absolutePath
            )
        )

        val profilesJson = buildDefaultProfiles()
        val transcodeResult = Mediator.requests.send(
            TranscodeVideoFileToAbrCli.Request(
                sourcePath = absolutePath,
                outputDir = file.filePath ?: throw KnownException.systemError("file_path 为空，无法生成输出"),
                profiles = profilesJson,
                segmentDurationSec = 6,
                taskId = null,
                status = null
            )
        )

        // TODO：文件持续时间和文件大小怎么办
        Mediator.commands.send(
            UpdateVideoFilePostTranscodeResultCmd.Request(
                videoFilePostId = file.id,
                success = transcodeResult.accepted,
                sourceWidth = probe.width,
                sourceHeight = probe.height,
                sourceBitrateKbps = probe.bitrateKbps,
                variants = transcodeResult.variants,
                duration = null,
                fileSize = null,
                failReason = transcodeResult.failReason
            )
        )
    }

    private fun resolveSourcePath(filePath: String?): String {
        if (filePath.isNullOrBlank()) throw KnownException.systemError("file_path 为空，无法转码")
        val abs = File(filePath)
        return if (abs.isAbsolute) {
            abs.absolutePath
        } else {
            File(fileProps.projectFolder + Constants.FILE_FOLDER + filePath).absolutePath
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
