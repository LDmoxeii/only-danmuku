package edu.only4.danmuku.application.commands.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import com.only.engine.exception.KnownException
import edu.only4.danmuku.domain._share.meta.video_hls_quality_auth.SVideoHlsQualityAuth
import edu.only4.danmuku.domain.aggregates.video_hls_quality_auth.VideoHlsQualityAuth
import edu.only4.danmuku.domain.aggregates.video_hls_quality_auth.enums.QualityAuthPolicy

import org.springframework.stereotype.Service

/**
 * 设置分辨率分级授权策略（单个质量）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object UpsertVideoHlsQualityAuthCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val quality = request.quality

            val authPolicy = QualityAuthPolicy.valueOfOrNull(request.authPolicy)
                ?: throw KnownException.illegalArgument("authPolicy=${request.authPolicy}")

            val existing = Mediator.repositories.find(
                SVideoHlsQualityAuth.predicate { schema ->
                    schema.all(
                        schema.fileId.eq(request.videoFilePostId),
                        schema.quality.eq(quality)
                    )
                }
            )
            existing.forEach(Mediator.uow::remove)

            Mediator.uow.persist(
                VideoHlsQualityAuth(
                    fileId = request.videoFilePostId,
                    quality = quality,
                    authPolicy = authPolicy,
                    remark = request.remark
                )
            )
            Mediator.uow.save()

            return Response(
                updated = true
            )
        }

    }

    data class Request(
        val videoFilePostId: Long,
        val quality: String,
        val authPolicy: Int,
        val remark: String? = null
    ) : RequestParam<Response>

    data class Response(
        val updated: Boolean = true
    )
}
