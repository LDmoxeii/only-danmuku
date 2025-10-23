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
object UpdateCustomerVideoSeriesSortCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 1. 批量查询所有待排序的系列（单次查询）
            val seriesList = Mediator.repositories.find(
                SCustomerVideoSeries.predicate { schema ->
                    schema.all(
                        schema.id.`in`(request.seriesIds),
                        schema.customerId.eq(request.userId)
                    )
                }
            )

            // 2. 校验：所有系列都存在（验证器已保证归属权）
            if (seriesList.size != request.seriesIds.toSet().size) {
                throw KnownException("部分系列不存在或不属于当前用户")
            }

            // 3. 按 ID 建立索引，便于按请求顺序更新
            val byId = seriesList.associateBy { it.id }

            // 4. 按照传入顺序设置 sort，从 1 开始递增
            var sortNo = 1
            request.seriesIds.forEach { seriesId ->
                val series = byId[seriesId]
                    ?: throw KnownException("系列不存在：$seriesId")
                series.updateSort(sortNo.toByte())
                sortNo += 1
            }

            // 5. 批量保存所有聚合根
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
