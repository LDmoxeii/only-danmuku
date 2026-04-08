package edu.only4.danmuku.application.commands.video_post

import com.only.engine.error.CommonErrors
import com.only.engine.exception.AppException
import com.only.engine.exception.BusinessException
import com.only.engine.exception.DependencyException
import com.only.engine.exception.RequestException
import com.only.engine.exception.SystemException
import edu.only4.danmuku.domain.shared.error.DanmukuBusinessErrors
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

object ChangeVideoPostInteractionCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {

            val videoPost = Mediator.repositories.findOne(
                SVideoPost.predicateById(request.videoPostId)
            ).getOrNull() ?: throw BusinessException(DanmukuBusinessErrors.RESOURCE_NOT_FOUND, "视频草稿不存在：${request.videoPostId}")

            if (videoPost.customerId != request.userId) {
                throw BusinessException(DanmukuBusinessErrors.OPERATION_FORBIDDEN, "无权限修改该视频互动设置")
            }

            videoPost.changeInteraction(request.interaction)

            Mediator.uow.save()
        }
    }

    data class Request(
        val videoPostId: Long,
        val userId: Long,
        val interaction: String?,
    ) : RequestParam<Unit>
}
