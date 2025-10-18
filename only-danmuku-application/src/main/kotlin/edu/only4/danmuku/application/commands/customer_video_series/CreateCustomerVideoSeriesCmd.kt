package edu.only4.danmuku.application.commands.customer_video_series

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import org.springframework.stereotype.Service

/**
 * 创建用户视频系列
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object CreateCustomerVideoSeriesCmd {

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
        /** 系列ID(更新时传) */
        val seriesId: Long? = null,
        /** 系列名称 */
        val seriesName: String,
        /** 系列描述 */
        val seriesDescription: String? = null,
        /** 视频ID列表(逗号分隔) */
        val videoIds: String? = null
    ) : RequestParam<Response>

    class Response(
    )
}
