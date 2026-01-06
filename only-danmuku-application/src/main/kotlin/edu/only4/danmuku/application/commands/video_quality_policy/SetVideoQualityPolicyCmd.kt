package edu.only4.danmuku.application.commands.video_quality_policy

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import edu.only4.danmuku.domain._share.meta.video_quality_policy.SVideoQualityPolicy
import edu.only4.danmuku.domain.aggregates.video_quality_policy.enums.QualityAuthPolicy
import edu.only4.danmuku.domain.aggregates.video_quality_policy.factory.VideoQualityPolicyFactory
import kotlin.jvm.optionals.getOrNull

import org.springframework.stereotype.Service

/**
 * 设置视频清晰度策略（转正后配置）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object SetVideoQualityPolicyCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val quality = request.quality.trim()
            if (quality.isBlank()) {
                throw KnownException.illegalArgument("quality")
            }

            val policy = Mediator.repositories.findOne(
                SVideoQualityPolicy.predicate { schema ->
                    schema.all(
                        schema.videoId.eq(request.videoId),
                        schema.fileIndex.eq(request.fileIndex),
                        schema.quality.eq(quality)
                    )
                }
            ).getOrNull()

            if (policy == null) {
                Mediator.factories.create(
                    VideoQualityPolicyFactory.Payload(
                        videoId = request.videoId,
                        fileIndex = request.fileIndex,
                        quality = quality,
                        authPolicy = request.authPolicy,
                        remark = request.remark
                    )
                )
            } else {
                policy.applyPolicy(
                    authPolicy = request.authPolicy,
                    remark = request.remark
                )
            }
            Mediator.uow.save()

            return Response(
                success = true
            )
        }

    }

    data class Request(
        val videoId: Long,
        val fileIndex: Int,
        val quality: String,
        val authPolicy: QualityAuthPolicy,
        val remark: String?
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true
    )
}
