package edu.only4.danmuku.application.commands.customer_video_series

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 更新用户视频系列视频
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object UpdateCustomerVideoSeriesVideosCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
            )
        }

    }

    data class Request(
        /** 用户ID */
        val userId: Long,
        /** 系列ID */
        val seriesId: Long,
        /** 视频ID列表(逗号分隔) - 可用于添加或删除 */
        val videoIds: String? = null,
        /** 是否删除操作 */
        val isDelete: Boolean = false
    ) : RequestParam<Response>

    class Response(
    )
}
