package edu.only4.danmuku.application.commands.video_post_processing

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 回写单个分P加密结果（处理聚合内变更）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object ApplyVideoPostProcessingEncryptResultCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
                success = TODO("set success"),
                failReason = TODO("set failReason")
            )
        }

    }

    data class Request(
        val videoPostId: Long,
        val fileIndex: Int,
        val success: Boolean,
        val encryptMethod: String = HLS_AES_128,
        val keyVersion: Int,
        val encryptedPrefix: String?,
        val encryptedMasterPath: String?,
        val encryptedVariants: String?,
        val failReason: String?
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true,
        val failReason: String?
    )
}
