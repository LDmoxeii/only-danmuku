package edu.only4.danmuku.application.commands.video_quality_policy

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import edu.only4.danmuku.domain.aggregates.video_quality_policy.enums.QualityAuthPolicy

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
            Mediator.uow.save()

            return Response(
                success = TODO("set success")
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
