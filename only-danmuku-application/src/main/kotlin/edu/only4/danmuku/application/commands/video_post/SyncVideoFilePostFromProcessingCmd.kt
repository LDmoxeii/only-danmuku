package edu.only4.danmuku.application.commands.video_post

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 处理完成后回填 VideoPost 子实体 VideoFilePost 与其分辨率档位（不暴露子实体ID）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object SyncVideoFilePostFromProcessingCmd {

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
        val videoPostId: Long,
        val fileIndex: Int,
        val transcodeOutputPrefix: String?,
        val encryptOutputPrefix: String?,
        val variantsJson: String?,
        val duration: Int?,
        val fileSize: Long?,
        val encryptMethod: String?,
        val keyVersion: Int?
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true
    )
}
