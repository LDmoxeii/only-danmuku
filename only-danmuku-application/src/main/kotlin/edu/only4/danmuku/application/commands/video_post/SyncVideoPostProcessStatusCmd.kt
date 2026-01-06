package edu.only4.danmuku.application.commands.video_post

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import edu.only4.danmuku.domain.aggregates.video_post.VideoFilePostVariant
import edu.only4.danmuku.domain.aggregates.video_post.enums.EncryptMethod
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import kotlin.jvm.optionals.getOrNull

import org.springframework.stereotype.Service

/**
 * 处理聚合事件驱动：更新稿件整体状态并回填稿件文件结果
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object SyncVideoPostProcessStatusCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val videoPost = Mediator.repositories.findOne(
                SVideoPost.predicateById(request.videoPostId)
            ).getOrNull() ?: throw KnownException("稿件不存在: ${request.videoPostId}")

            videoPost.applyProcessStatus(request.targetStatus)
            request.duration?.let { videoPost.updateDuration(it) }

            request.fileList.forEach { fileItem ->
                val file = videoPost.videoFilePosts.firstOrNull { it.fileIndex == fileItem.fileIndex }
                    ?: throw KnownException("分P不存在: videoPostId=${request.videoPostId}, fileIndex=${fileItem.fileIndex}")

                file.applyTranscodeResult(
                    outputPrefix = fileItem.transcodeOutputPrefix,
                    duration = fileItem.duration,
                    fileSize = fileItem.fileSize
                )
                val variants = fileItem.variants.map { payload ->
                    VideoFilePostVariant(
                        quality = payload.quality,
                        width = payload.width,
                        height = payload.height,
                        videoBitrateKbps = payload.videoBitrateKbps,
                        audioBitrateKbps = payload.audioBitrateKbps,
                        bandwidthBps = payload.bandwidthBps,
                        playlistPath = payload.playlistPath,
                        segmentPrefix = payload.segmentPrefix,
                        segmentDuration = payload.segmentDuration
                    )
                }
                file.syncVariants(variants)

                if (!fileItem.encryptMethod.isNullOrBlank() && !fileItem.encryptOutputPrefix.isNullOrBlank()) {
                    val method = runCatching { EncryptMethod.valueOf(fileItem.encryptMethod) }
                        .getOrNull() ?: throw KnownException.illegalArgument("encryptMethod")
                    file.applyEncryptResult(
                        success = true,
                        method = method,
                        keyVersion = fileItem.keyVersion,
                        outputPrefix = fileItem.encryptOutputPrefix
                    )
                }
            }
            Mediator.uow.save()

            return Response(
                success = true
            )
        }

    }

    data class Request(
        val videoPostId: Long,
        val targetStatus: VideoStatus,
        val duration: Int?,
        val failReason: String?,
        val fileList: List<FileItem> = emptyList()
    ) : RequestParam<Response>

    data class FileItem(
        val fileIndex: Int,
        val transcodeOutputPrefix: String?,
        val encryptOutputPrefix: String?,
        val variants: List<VariantItem> = emptyList(),
        val duration: Int?,
        val fileSize: Long?,
        val encryptMethod: String?,
        val keyVersion: Int?
    )

    data class Response(
        val success: Boolean = true
    )

    data class VariantItem(
        val quality: String = "",
        val width: Int = 0,
        val height: Int = 0,
        val videoBitrateKbps: Int = 0,
        val audioBitrateKbps: Int = 0,
        val bandwidthBps: Int = 0,
        val playlistPath: String = "",
        val segmentPrefix: String? = null,
        val segmentDuration: Int? = null,
    )
}
