package edu.only4.danmuku.application.commands.video_draft

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import edu.only4.danmuku.domain.aggregates.video.enums.PostType
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 保存视频信息和文件
 */
object SaveVideoInfoCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val videoId = request.videoId ?: throw KnownException("缺少视频ID")

            val totalDuration = request.uploadFileList.sumOf { it.duration }

            // 查找草稿
            val draft = Mediator.repositories.findFirst(
                SVideoPost.predicate { schema ->
                    schema.all(
                        schema.id eq videoId,
                        schema.customerId eq request.customerId
                    )
                },
                persist = false
            ).getOrNull() ?: throw KnownException("视频草稿不存在，无法保存")

            // 更新草稿基础信息
            draft.videoCover = request.videoCover
            draft.videoName = request.videoName
            draft.pCategoryId = request.pCategoryId.toLong()
            draft.categoryId = request.categoryId?.toLong()
            draft.postType = PostType.valueOf(request.postType)
            draft.tags = request.tags
            draft.introduction = request.introduction
            draft.interaction = request.interaction
            draft.duration = totalDuration

            // 先移除原有文件草稿（如需重建文件清单）
            // TODO 删除文件草稿
            // 如需保存新文件草稿，可在此处创建对应记录（略）

            Mediator.uow.save()
            return Response()
        }
    }

    data class Request(
        /** 视频ID(更新时传) */
        val videoId: Long? = null,
        /** 用户ID */
        val customerId: Long,
        /** 视频封面 */
        val videoCover: String,
        /** 视频名称 */
        val videoName: String,
        /** 父分类ID */
        val pCategoryId: Int,
        /** 分类ID */
        val categoryId: Int? = null,
        /** 上传类型(0自制/1转载) */
        val postType: Int,
        /** 标签 */
        val tags: String,
        /** 简介 */
        val introduction: String? = null,
        /** 互动设置 */
        val interaction: String? = null,
        /** 上传文件列表 */
        val uploadFileList: List<VideoFileInfo>,
    ) : RequestParam<Response>

    data class VideoFileInfo(
        val uploadId: String,
        val fileIndex: Int,
        val fileName: String,
        val fileSize: Long,
        val duration: Int,
    )

    class Response
}
