package edu.only4.danmuku.application.commands.video_encrypt

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 批量生成清晰度独立 HLS key（同批次 keyVersion）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
object GenerateVideoHlsQualityKeysCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
                keyVersion = TODO("set keyVersion"),
                keysJson = TODO("set keysJson")
            )
        }

    }

    data class Request(
        val videoFilePostId: Long,
        val videoFileId: Long?,
        val qualities: List<String>,
        val method: String = "HLS_AES_128",
        val keyBytes: Int = 16
    ) : RequestParam<Response>

    data class Response(
        val keyVersion: Int,
        val keysJson: String
    )
}
