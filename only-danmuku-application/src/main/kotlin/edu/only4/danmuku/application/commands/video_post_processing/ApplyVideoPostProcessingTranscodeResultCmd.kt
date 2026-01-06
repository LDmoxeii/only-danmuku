package edu.only4.danmuku.application.commands.video_post_processing

import com.only.engine.exception.KnownException
import com.only.engine.json.misc.JsonUtils
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_post_processing.SVideoPostProcessing
import edu.only4.danmuku.domain.aggregates.video_post_processing.VideoPostProcessingVariant
import kotlin.jvm.optionals.getOrNull

import org.springframework.stereotype.Service

/**
 * 回写单个分P转码结果（处理聚合内变更）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object ApplyVideoPostProcessingTranscodeResultCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val processing = Mediator.repositories.findFirst(
                SVideoPostProcessing.predicate { schema ->
                    schema.videoPostId.eq(request.videoPostId)
                }
            ).getOrNull() ?: throw KnownException("处理聚合不存在: ${request.videoPostId}")

            val variants = parseVariants(request.variantsJson)
            processing.applyTranscodeResult(
                fileIndex = request.fileIndex,
                success = request.success,
                outputPrefix = request.outputPrefix,
                outputPath = request.outputPath,
                duration = request.duration,
                fileSize = request.fileSize,
                variantsJson = request.variantsJson,
                failReason = request.failReason,
                variants = variants
            )

            Mediator.uow.save()

            return Response(
                success = request.success,
                failReason = request.failReason
            )
        }

    }

    data class Request(
        val videoPostId: Long,
        val fileIndex: Int,
        val success: Boolean,
        val outputPrefix: String?,
        val outputPath: String?,
        val duration: Int?,
        val fileSize: Long?,
        val variantsJson: String?,
        val failReason: String?
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true,
        val failReason: String?
    )

    private fun parseVariants(variantsJson: String?): List<VideoPostProcessingVariant> {
        if (variantsJson.isNullOrBlank()) return emptyList()
        val payloads = JsonUtils.parseArray(variantsJson, VariantPayload::class.java)
        if (payloads.isEmpty()) return emptyList()
        return payloads.map { payload ->
            VideoPostProcessingVariant(
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
    }

    data class VariantPayload(
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
