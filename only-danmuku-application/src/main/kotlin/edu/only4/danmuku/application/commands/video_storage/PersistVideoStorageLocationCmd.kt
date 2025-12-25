package edu.only4.danmuku.application.commands.video_storage

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import com.only.engine.exception.KnownException
import edu.only4.danmuku.domain._share.meta.video_file_post.SVideoFilePost
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 更新视频文件的存储前缀
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
object PersistVideoStorageLocationCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val file = Mediator.repositories.findOne(
                SVideoFilePost.predicateById(request.videoFilePostId)
            ).getOrNull() ?: throw KnownException("分P不存在: ${request.videoFilePostId}")
            file.updateStoragePath(request.storagePrefix)
            Mediator.uow.save()

            return Response(
                updated = true
            )
        }

    }

    data class Request(
        val videoFilePostId: Long,
        val storagePrefix: String
    ) : RequestParam<Response>

    data class Response(
        val updated: Boolean = true
    )
}
