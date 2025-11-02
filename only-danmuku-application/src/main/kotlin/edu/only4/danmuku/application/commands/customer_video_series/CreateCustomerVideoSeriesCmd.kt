package edu.only4.danmuku.application.commands.customer_video_series

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.customer_video_series.SCustomerVideoSeries
import edu.only4.danmuku.domain.aggregates.customer_video_series.factory.CustomerVideoSeriesFactory
import edu.only4.danmuku.application.validator.UniqueSeriesNameForUser
import edu.only4.danmuku.application.validator.VideoIdsBelongToUser
import edu.only4.danmuku.application.validator.SeriesVideoCountLimit
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 创建用户视频系列
 *
 * 该文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object CreateCustomerVideoSeriesCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val normalizedName = request.seriesName.trim()
            val normalizedDescription = request.seriesDescription?.trim()?.takeIf { it.isNotEmpty() }

            val incomingVideoIds = parseVideoIds(request.videoIds)

            val targetSeries = if (request.seriesId != null) {
                val series = Mediator.repositories.findFirst(
                    SCustomerVideoSeries.predicateById(request.seriesId)
                ).getOrNull() ?: throw KnownException("系列不存在: ${request.seriesId}")

                if (series.customerId != request.userId) {
                    throw KnownException("没有权限操作该系列")
                }

                series.updateBasicInfo(normalizedName, normalizedDescription)

                incomingVideoIds?.let { videoIds -> series.replaceVideos(request.userId, videoIds) }
                series
            } else {
                val sort = determineNextSort(request.userId)
                val series = Mediator.factories.create(
                    CustomerVideoSeriesFactory.Payload(
                        customerId = request.userId,
                        seriesName = normalizedName,
                        seriesDescription = normalizedDescription,
                        sort = sort
                    )
                )

                val videosToAttach = incomingVideoIds ?: emptyList()
                if (videosToAttach.isNotEmpty()) {
                    series.replaceVideos(request.userId, videosToAttach)
                }
                series
            }

            Mediator.uow.save()
            return Response(seriesId = targetSeries.id)
        }

        private fun determineNextSort(userId: Long): Byte {
            val currentMax = Mediator.repositories.findFirst(
                SCustomerVideoSeries.predicate(
                    { schema -> schema.customerId eq userId },
                    { schema -> schema.sort.desc() }
                ),
                persist = false
            ).getOrNull()?.sort?.toInt() ?: 0
            val next = currentMax + 1
            if (next > Byte.MAX_VALUE) {
                throw KnownException("系列数量已达到上限")
            }
            return next.toByte()
        }

        private fun parseVideoIds(rawVideoIds: String?): List<Long>? {
            rawVideoIds ?: return null
            val result = mutableListOf<Long>()
            val dedupe = mutableSetOf<Long>()
            rawVideoIds.split(',').forEach { part ->
                val trimmed = part.trim()
                if (trimmed.isEmpty()) {
                    return@forEach
                }
                val id = trimmed.toLongOrNull() ?: throw KnownException("无效的视频ID: $trimmed")
                if (id <= 0) {
                    throw KnownException("无效的视频ID: $trimmed")
                }
                if (dedupe.add(id)) {
                    result.add(id)
                }
            }
            return result
        }
    }

    @UniqueSeriesNameForUser(userIdField = "userId", seriesIdField = "seriesId", seriesNameField = "seriesName")
    @VideoIdsBelongToUser(userIdField = "userId", videoIdsField = "videoIds")
    @SeriesVideoCountLimit(videoIdsField = "videoIds")
    data class Request(
        /** 用户ID */
        val userId: Long,
        /** 系列ID(编辑时用) */
        val seriesId: Long? = null,
        /** 系列名称 */
        @field:NotBlank(message = "系列名称不能为空")
        @field:Size(max = 100, message = "系列名称长度不能超过100个字符")
        val seriesName: String,
        /** 系列描述 */
        @field:Size(max = 200, message = "系列描述长度不能超过200个字符")
        val seriesDescription: String? = null,
        /** 视频ID列表(逗号分隔) */
        val videoIds: String? = null,
    ) : RequestParam<Response>

    data class Response(
        /** 系列ID */
        val seriesId: Long,
    )
}
