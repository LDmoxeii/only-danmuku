package edu.only4.danmuku.application.commands.video_post

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command

import com.only.engine.exception.KnownException
import edu.only4.danmuku.domain._share.meta.video_file_post.SVideoFilePost
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import edu.only4.danmuku.domain.aggregates.video_file_post.enums.TransferResult
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 汇总稿件下文件转码结果，刷新 VideoPost 状态与总时长
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
object RefreshVideoPostTranscodeStatusCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val post = Mediator.repositories.findOne(
                SVideoPost.predicateById(request.videoPostId)
            ).getOrNull() ?: throw KnownException("视频稿件不存在: ${request.videoPostId}")

            val files = Mediator.repositories.find(
                SVideoFilePost.predicate { it.videoId eq request.videoPostId },
                persist = false
            )

            val total = files.size
            val success = files.count { it.transferResult == TransferResult.SUCCESS }
            val failed = files.count { it.transferResult == TransferResult.FAILED }
            val totalDuration = files.mapNotNull { it.duration }.sum()

            when {
                failed > 0 -> post.markTranscodeFailed()
                total > 0 && success == total -> {
                    post.updateDuration(totalDuration)
                    post.markPendingReview()
                }
                else -> post.markTranscoding()
            }

            Mediator.uow.save()

            return Response(
                videoPostId = post.id,
                successCount = success,
                failedCount = failed,
                totalCount = total
            )
        }

    }

    data class Request(
        val videoPostId: Long,
    ) : RequestParam<Response>

    data class Response(
        val videoPostId: Long,
        val successCount: Int,
        val failedCount: Int,
        val totalCount: Int,
    )
}
