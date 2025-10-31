package edu.only4.danmuku.application.commands.customer_video_series

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
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
            if (normalizedName.isEmpty()) {
                throw KnownException("ϵ�����Ʋ���Ϊ��")
            }
            val normalizedDescription = request.seriesDescription?.trim()?.takeIf { it.isNotEmpty() }

            val series = Mediator.repositories.findFirst(
                SCustomerVideoSeries.predicateById(request.seriesId)
            ).getOrNull() ?: throw KnownException("ϵ�в�����: ${request.seriesId}")

            if (series.customerId != request.userId) {
                throw KnownException("û��Ȩ�޲�����ϵ��")
            }

            // 名称唯一性：同一用户下重名校验（排除自己）
            val duplicated = Mediator.repositories.findFirst(
                SCustomerVideoSeries.predicate { schema ->
                    schema.all(
                        schema.customerId eq request.userId,
                        schema.seriesName eq normalizedName
                    )
                },
                persist = false
            ).getOrNull()

            if (duplicated != null && duplicated.id != series.id) {
                throw KnownException("ϵ�������Ѵ���")
            }

            series.updateBasicInfo(normalizedName, normalizedDescription)
            Mediator.uow.save()
            return Response(seriesId = series.id)
        }
    }

    data class Request(
        val userId: Long,
        val seriesId: Long,
        @field:NotBlank(message = "ϵ�����Ʋ���Ϊ��")
        @field:Size(max = 100, message = "ϵ�����Ƴ��Ȳ��ܳ���100")
        val seriesName: String,
        @field:Size(max = 200, message = "ϵ���������Ȳ��ܳ���200")
        val seriesDescription: String? = null,
    ) : RequestParam<Response>

    data class Response(
        val seriesId: Long,
    )
}

