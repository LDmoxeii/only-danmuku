package edu.only4.danmuku.application.commands.video_file_draft

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_file_draft.SVideoFileDraft
import org.springframework.stereotype.Service

/**
 * 删除单个视频文件草稿（按 uploadId 删除）
 */
object DeleteVideoFileDraftCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.repositories.remove(
                SVideoFileDraft.predicate { schema ->
                    schema.all(
                        schema.uploadId eq request.uploadId,
                        schema.customerId eq request.customerId,
                        schema.videoId eq request.videoId,
                    )
                }
            )

            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        val customerId: Long,
        val videoId: Long,
        val uploadId: Long,
    ) : RequestParam<Response>

    class Response
}
