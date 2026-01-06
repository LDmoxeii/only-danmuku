package edu.only4.danmuku.application.commands.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 生成清晰度密钥（不暴露子实体ID）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object GenerateVideoPostQualityKeysCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val generated = Mediator.commands.send(
                GenerateVideoHlsQualityKeysCmd.Request(
                    videoPostId = request.videoPostId,
                    fileIndex = request.fileIndex,
                    qualities = request.qualities,
                    method = request.method,
                    keyBytes = request.keyBytes
                )
            )
            return Response(
                keyVersion = generated.keyVersion,
                keysJson = generated.keysJson
            )
        }

    }

    data class Request(
        val videoPostId: Long,
        val fileIndex: Int,
        val qualities: List<String>,
        val method: String = "HLS_AES_128",
        val keyBytes: Int = 16
    ) : RequestParam<Response>

    data class Response(
        val keyVersion: Int,
        val keysJson: String
    )
}
