package edu.only4.danmuku.application.commands.video_comment

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 发表评论
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object PostCommentCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
            )
        }

    }

    data class Request(
        /** 视频ID */
        val videoId: Long,
        /** 回复评论ID */
        val replyCommentId: Long? = null,
        /** 评论内容 */
        val content: String,
        /** 图片路径 */
        val imgPath: String? = null
    ) : RequestParam<Response>

    data class Response(
        /** 评论ID */
        val commentId: Long
    )
}
