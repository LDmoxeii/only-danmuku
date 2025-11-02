package edu.only4.danmuku.application.commands.customer_video_series

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validator.UniqueSeriesNameForUser
import edu.only4.danmuku.domain._share.meta.customer_video_series.SCustomerVideoSeries
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 更新用户视频系列基础信息（名称、描述）
 */
object UpdateCustomerVideoSeriesInfoCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val normalizedName = request.seriesName.trim()
            val normalizedDescription = request.seriesDescription?.trim()?.takeIf { it.isNotEmpty() }

            val series = Mediator.repositories.findFirst(
                SCustomerVideoSeries.predicateById(request.seriesId)
            ).getOrNull() ?: throw KnownException("系列不存在: ${request.seriesId}")

            if (series.customerId != request.userId) {
                throw KnownException("没有权限操作该系列")
            }

            series.updateBasicInfo(normalizedName, normalizedDescription)
            Mediator.uow.save()
            return Response(seriesId = series.id)
        }
    }

    @UniqueSeriesNameForUser(userIdField = "userId", seriesIdField = "seriesId", seriesNameField = "seriesName")
    data class Request(
        val userId: Long,
        val seriesId: Long,
        @field:NotBlank(message = "系列名称不能为空")
        @field:Size(max = 100, message = "系列名称长度不能超过100")
        val seriesName: String,
        @field:Size(max = 200, message = "系列描述长度不能超过200")
        val seriesDescription: String? = null,
    ) : RequestParam<Response>

    data class Response(
        val seriesId: Long,
    )
}
