package edu.only4.danmuku.application.commands.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 根据加密 CLI 结果更新 VideoFilePost 状态，落密钥/目录元数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object PersistVideoEncryptResultCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
                encryptStatus = TODO("set encryptStatus")
            )
        }

    }

    data class Request(
        val fileId: Long,
        val success: Boolean,
        val encryptMethod: String = HLS_AES_128,
        val keyId: String?,
        val keyVersion: Int?,
        val keyQuality: String?,
        val encryptedMasterPath: String?,
        val encryptedVariants: String?,
        val failReason: String?
    ) : RequestParam<Response>

    data class Response(
        val encryptStatus: Int
    )
}
