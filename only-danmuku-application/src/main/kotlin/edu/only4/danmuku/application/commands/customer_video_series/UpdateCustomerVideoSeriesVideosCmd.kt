package edu.only4.danmuku.application.commands.customer_video_series

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.customer_video_series.SCustomerVideoSeries
import edu.only4.danmuku.domain._share.meta.video.SVideo
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
            ).getOrNull() ?: throw KnownException("ϵ�в�����: ${request.seriesId}")

            if (series.customerId != request.userId) {
                throw KnownException("û��Ȩ�޲�����ϵ��")
            }

            val incomingVideoIds = parseVideoIds(request.videoIds)
            if (incomingVideoIds.isEmpty()) {
                throw KnownException("��ƵID�б�����Ϊ��")
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
                ensureVideosBelongToUser(request.userId, incomingVideoIds)
                val dedupe = currentVideoIds.toMutableList()
                var changed = false
                incomingVideoIds.forEach { id ->
                    if (!dedupe.contains(id)) {
                        dedupe.add(id)
                        changed = true
                    }
                }
                if (!changed) {
                    return Response()
                }
                ensureVideoListSize(dedupe.size)
                dedupe
            }

            ensureVideoListSize(updatedVideoIds.size)
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
                val id = trimmed.toLongOrNull() ?: throw KnownException("��Ч����ƵID: $trimmed")
                if (id <= 0) {
                    throw KnownException("��Ч����ƵID: $trimmed")
                }
                if (dedupe.add(id)) {
                    result.add(id)
                }
            }
            return result
        }

        private fun ensureVideosBelongToUser(userId: Long, videoIds: List<Long>) {
            if (videoIds.isEmpty()) {
                return
            }
            val videos = Mediator.repositories.find(
                SVideo.predicate { schema ->
                    schema.all(
                        schema.customerId eq userId,
                        schema.id.`in`(videoIds)
                    )
                }
            )
            if (videos.size != videoIds.size) {
                val foundIds = videos.map { it.id }.toSet()
                val missing = videoIds.filterNot(foundIds::contains)
                throw KnownException("������Ƶ������: ${missing.joinToString(",")}")
            }
        }

        private fun ensureVideoListSize(size: Int) {
            if (size > Byte.MAX_VALUE) {
                throw KnownException("����ϵ�����֧�� ${Byte.MAX_VALUE} ����Ƶ")
            }
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

    class Response
}
