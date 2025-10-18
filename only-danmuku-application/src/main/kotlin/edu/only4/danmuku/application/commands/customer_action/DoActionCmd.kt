package edu.only4.danmuku.application.commands.customer_action

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 执行用户行为
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object DoActionCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
            )
        }

    }

    class Request(
        /** 用户ID */
        val userId: Long,
        /** 视频ID */
        val videoId: Long,
        /** 行为类型 (0:点赞, 1:收藏, 2:投币) */
        val actionType: Int,
        /** 行为次数 (1或2，用于投币) */
        val actionCount: Int = 1,
        /** 评论ID (可选，针对评论的行为) */
        val commentId: Long? = null
    ) : RequestParam<Response>

    class Response(
    )
}
