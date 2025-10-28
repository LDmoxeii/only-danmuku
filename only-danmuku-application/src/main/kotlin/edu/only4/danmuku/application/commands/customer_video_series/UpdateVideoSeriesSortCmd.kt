package edu.only4.danmuku.application.commands.customer_video_series

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.SeriesBelongToUser
import edu.only4.danmuku.domain._share.meta.customer_video_series.SCustomerVideoSeries
import jakarta.validation.constraints.NotEmpty
import org.springframework.stereotype.Service

/**
 * 更新用户视频系列排序
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object UpdateVideoSeriesSortCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val seriesList = Mediator.repositories.find(
                SCustomerVideoSeries.predicateByIds(request.seriesIds)
            )

            if (seriesList.size != request.seriesIds.toSet().size) {
                throw KnownException("部分系列不存在")
            }

            val byId = seriesList.associateBy { it.id }

            var sortNo = 1
            request.seriesIds.forEach { seriesId ->
                val series = byId[seriesId]
                    ?: throw KnownException("系列不存在：$seriesId")
                series.updateSort(sortNo.toByte())
                sortNo += 1
            }

            Mediator.uow.save()

            return Response()
        }
    }

    @SeriesBelongToUser(userIdField = "userId", seriesIdsField = "seriesIds")
    data class Request(
        /** 用户ID */
        val userId: Long,

        /** 系列ID列表（按新的排序顺序） */
        @field:NotEmpty(message = "系列ID列表不能为空")
        val seriesIds: List<Long>
    ) : RequestParam<Response>

    class Response
}
