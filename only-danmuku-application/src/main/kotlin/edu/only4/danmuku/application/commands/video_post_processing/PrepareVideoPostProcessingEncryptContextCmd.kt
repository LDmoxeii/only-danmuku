package edu.only4.danmuku.application.commands.video_post_processing

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 准备单分P加密上下文（分配 keyVersion 并返回输出路径）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
object PrepareVideoPostProcessingEncryptContextCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
                keyVersion = TODO("set keyVersion"),
                transcodeOutputPrefix = TODO("set transcodeOutputPrefix"),
                encryptOutputDir = TODO("set encryptOutputDir")
            )
        }

    }

    data class Request(
        val videoPostId: Long,
        val fileIndex: Int,
        val encryptMethod: String = HLS_AES_128
    ) : RequestParam<Response>

    data class Response(
        val keyVersion: Int,
        val transcodeOutputPrefix: String?,
        val encryptOutputDir: String?
    )
}
