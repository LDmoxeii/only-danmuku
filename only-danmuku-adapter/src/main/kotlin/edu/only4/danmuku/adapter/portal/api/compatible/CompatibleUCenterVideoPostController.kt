package edu.only4.danmuku.adapter.portal.api.compatible

import com.only.engine.json.misc.JsonUtils
import com.only.engine.json.validate.JsonPattern
import com.only.engine.json.validate.JsonType
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.UCenterGetVideoByVideoId
import edu.only4.danmuku.adapter.portal.api.payload.UCenterGetVideoCountInfo
import edu.only4.danmuku.adapter.portal.api.payload.UCenterLoadVideoList
import edu.only4.danmuku.adapter.portal.api.payload.UCenterPostVideo
import edu.only4.danmuku.application.commands.video_file_post.CreateVideoFilePostCmd
import edu.only4.danmuku.application.commands.video_file_post.DeleteVideoFilePostCmd
import edu.only4.danmuku.application.commands.video_post.ChangeVideoPostInteractionCmd
import edu.only4.danmuku.application.commands.video_post.CreateVideoPostCmd
import edu.only4.danmuku.application.commands.video_post.DeleteVideoPostCmd
import edu.only4.danmuku.application.commands.video_post.RefreshVideoPostTranscodeStatusCmd
import edu.only4.danmuku.application.commands.video_post.UpdateVideoPostCmd
import edu.only4.danmuku.application.queries.video_draft.GetUserVideoPostQry
import edu.only4.danmuku.application.queries.video_draft.GetVideoDraftCountByStatusQry
import edu.only4.danmuku.application.queries.video_draft.GetVideoPostInfoQry
import edu.only4.danmuku.application.queries.video_file_post.GetVideoFilePostsByPostIdQry
import edu.only4.danmuku.domain.aggregates.video_post.enums.PostType
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ucenter")
@Validated
class CompatibleUCenterVideoPostController {

    @PostMapping("/postVideo")
    fun postVideo(
        videoId: Long?,
        @NotEmpty videoCover: String,
        @NotEmpty @Size(max = 100) videoName: String,
        parentCategoryId: Long,
        categoryId: Long?,
        postType: Int,
        originInfo: String?,
        @NotEmpty @Size(max = 300) tags: String,
        @Size(max = 2000) introduction: String?,
        @Size(max = 3) interaction: String?,
        @NotEmpty @JsonPattern(type = JsonType.ARRAY) uploadFileList: String,
    ): UCenterPostVideo.Response {
        val currentUserId = LoginHelper.getUserId()!!

        // 解析上传文件列表 JSON（兼容 uploadId/fileId 混合场景）
        val mixedList = JsonUtils.parseArray(uploadFileList, UCenterPostVideo.MixedFileItem::class.java)

        if (videoId == null) {
            val createResp = Mediator.commands.send(
                CreateVideoPostCmd.Request(
                    customerId = currentUserId,
                    videoCover = videoCover,
                    videoName = videoName,
                    parentCategoryId = parentCategoryId,
                    categoryId = categoryId,
                    postType = PostType.valueOf(postType),
                    originInfo = originInfo,
                    tags = tags,
                    introduction = introduction,
                    interaction = interaction,
                )
            )
            val newVideoId = createResp.videoId
            mixedList.forEachIndexed { idx, item ->
                Mediator.commands.send(
                    CreateVideoFilePostCmd.Request(
                        uploadId = item.uploadId,
                        customerId = currentUserId,
                        videoId = newVideoId,
                        fileIndex = idx + 1,
                        fileName = item.fileName,
                    )
                )
            }
        } else {
            Mediator.commands.send(
                UpdateVideoPostCmd.Request(
                    videoPostId = videoId,
                    customerId = currentUserId,
                    videoName = videoName,
                    videoCover = videoCover,
                    pCategoryId = parentCategoryId,
                    categoryId = categoryId,
                    postType = PostType.valueOf(postType),
                    originInfo = originInfo,
                    tags = tags,
                    introduction = introduction,
                    interaction = interaction,
                )
            )

            val exists = Mediator.queries.send(
                GetVideoFilePostsByPostIdQry.Request(videoPostId = videoId)
            ).files
            val existingIds = exists.map { it.videoFilePostId }.toMutableSet()
            val desiredIds = mutableSetOf<Long>()

            mixedList.forEachIndexed { idx, item ->
                val fileId = item.fileId
                if (fileId != null) {
                    desiredIds.add(fileId)
                } else {
                    Mediator.commands.send(
                        CreateVideoFilePostCmd.Request(
                            uploadId = item.uploadId,
                            customerId = currentUserId,
                            videoId = videoId,
                            fileIndex = idx + 1,
                            fileName = item.fileName,
                        )
                    )
                }
            }

            existingIds.minus(desiredIds).forEach { removeId ->
                Mediator.commands.send(DeleteVideoFilePostCmd.Request(videoFilePostId = removeId))
            }

            Mediator.commands.send(
                RefreshVideoPostTranscodeStatusCmd.Request(videoPostId = videoId)
            )
        }

        return UCenterPostVideo.Response()
    }

    @PostMapping("/loadVideoList")
    fun getVideoPostPage(@RequestBody @Validated request: UCenterLoadVideoList.Request): PageData<UCenterLoadVideoList.VideoItem> {
        val currentUserId = LoginHelper.getUserId()!!

        val queryRequest = GetUserVideoPostQry.Request(
            userId = currentUserId,
            status = if (request.status == VideoStatus.UNKNOW) null else request.status,
            videoNameFuzzy = request.videoNameFuzzy,
            excludeStatusArray = if (request.status == VideoStatus.UNKNOW) listOf(VideoStatus.REVIEW_PASSED, VideoStatus.REVIEW_FAILED) else null // 进行中排除审核通过和不通过
        ).apply {
            pageNum = request.pageNum
            pageSize = 999
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { UCenterLoadVideoList.Converter.INSTANCE.fromApp(it) },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/getVideoCountInfo")
    fun getVideoPostCountInfo(): UCenterGetVideoCountInfo.Response {
        val currentUserId = LoginHelper.getUserId()!!

        // 查询审核通过的数量 (status = 4)
        val auditPassCount = Mediator.queries.send(
            GetVideoDraftCountByStatusQry.Request(
                userId = currentUserId,
                status = VideoStatus.REVIEW_PASSED
            )
        ).count

        // 查询审核失败的数量 (status = 5)
        val auditFailCount = Mediator.queries.send(
            GetVideoDraftCountByStatusQry.Request(
                userId = currentUserId,
                status = VideoStatus.REVIEW_FAILED
            )
        ).count

        // 查询进行中的数量 (排除审核通过和不通过)
        val inProgress = Mediator.queries.send(
            GetVideoDraftCountByStatusQry.Request(
                userId = currentUserId,
                excludeStatusArray = listOf(VideoStatus.REVIEW_PASSED, VideoStatus.REVIEW_FAILED)
            )
        ).count

        return UCenterGetVideoCountInfo.Response(
            auditPassCount = auditPassCount,
            auditFailCount = auditFailCount,
            inProgress = inProgress
        )
    }

    @PostMapping("/getVideoByVideoId")
    fun getVideoPostByVideoPostId(
        videoPostId: Long,
    ): UCenterGetVideoByVideoId.Response {
        val currentUserId = LoginHelper.getUserId()!!

        val queryResult = Mediator.queries.send(
            GetVideoPostInfoQry.Request(
                videoPostId = videoPostId,
                userId = currentUserId
            )
        )

        return UCenterGetVideoByVideoId.Converter.INSTANCE.fromApp(queryResult)
    }

    @PostMapping("/saveVideoInteraction")
    fun saveVideoPostInteraction(
        videoPostId: Long,
        interaction: String,
    ) {
        val userId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            ChangeVideoPostInteractionCmd.Request(
                videoId = videoPostId,
                userId = userId,
                interaction = interaction
            )
        )
    }

    @PostMapping("/deleteVideo")
    fun deleteVideo(
        videoPostId: Long,
    ) {
        val currentUserId = LoginHelper.getUserId()!!
        Mediator.commands.send(
            DeleteVideoPostCmd.Request(
                videoId = videoPostId,
                operatorId = currentUserId
            )
        )
    }

}

