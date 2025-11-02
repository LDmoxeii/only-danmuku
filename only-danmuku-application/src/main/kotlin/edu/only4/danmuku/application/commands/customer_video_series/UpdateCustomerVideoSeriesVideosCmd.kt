package edu.only4.danmuku.application.commands.customer_video_series

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.customer_video_series.SCustomerVideoSeries
import edu.only4.danmuku.application.validator.VideoIdsBelongToUser
import edu.only4.danmuku.application.validator.SeriesVideoCountLimit
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull
import java.util.LinkedHashSet

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
            val series = Mediator.repositories.findFirst(
                SCustomerVideoSeries.predicateById(request.seriesId)
            ).getOrNull() ?: throw KnownException("系列不存在: ${request.seriesId}")

            if (series.customerId != request.userId) {
                throw KnownException("没有权限操作该系列")
            }

            val incomingVideoIds = parseVideoIds(request.videoIds)
            if (incomingVideoIds.isEmpty()) {
                return Response()
            }

            val currentVideoIds = series.customerVideoSeriesVideos
                .sortedBy { it.sort }
                .map { it.videoId }

            val updatedVideoIds = if (request.isDelete) {
                val removalSet = incomingVideoIds.toSet()
                val filtered = currentVideoIds.filterNot(removalSet::contains)
                if (filtered.size == currentVideoIds.size) {
                    return Response()
                }
                filtered
            } else {
                val currentSet = currentVideoIds.toSet()
                val incomingSet = incomingVideoIds.toSet()

                if (incomingSet == currentSet && incomingVideoIds.size == currentVideoIds.size) {
                    incomingVideoIds
                } else {
                    val additions = incomingVideoIds.filterNot(currentSet::contains)
                    currentVideoIds + additions
                }
            }

            series.replaceVideos(request.userId, updatedVideoIds)
            Mediator.uow.save()

            return Response()
        }

        private fun parseVideoIds(rawVideoIds: String?): List<Long> {
            val sanitized = rawVideoIds?.trim() ?: return emptyList()
            if (sanitized.isEmpty()) {
                return emptyList()
            }
            val normalized = sanitized
                .removePrefix("[")
                .removeSuffix("]")
                .replace("\n", ",")
                .replace("\r", ",")
                .replace("\t", ",")
            val result = mutableListOf<Long>()
            val dedupe = LinkedHashSet<Long>()
            normalized.split(',', '，').forEach { item ->
                val trimmed = item.trim().trim('"')
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

    @VideoIdsBelongToUser(userIdField = "userId", videoIdsField = "videoIds")
    @SeriesVideoCountLimit(videoIdsField = "videoIds")
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

    class Response
}
