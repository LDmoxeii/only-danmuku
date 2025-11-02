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
import edu.only4.danmuku.application.commands.video_post.ChangeVideoPostInteractionCmd
import edu.only4.danmuku.application.commands.video_post.CreateVideoPostCmd
import edu.only4.danmuku.application.commands.video_post.DeleteVideoPostCmd
import edu.only4.danmuku.application.commands.video_post.UpdateVideoPostCmd
import edu.only4.danmuku.application.queries.video_draft.GetUserVideoPostQry
import edu.only4.danmuku.application.queries.video_draft.GetVideoDraftCountByStatusQry
import edu.only4.danmuku.application.queries.video_draft.GetVideoPostInfoQry
import edu.only4.danmuku.domain.aggregates.video.enums.PostType
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
            // 新增：创建视频草稿，仅保留带 uploadId 的条目
            val createFileList = mixedList.mapIndexed { idx, item ->
                CreateVideoPostCmd.VideoFileInfo(
                    uploadId = item.uploadId,
                    fileIndex = idx + 1,
                    fileName = item.fileName,
                )
            }
            Mediator.commands.send(
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
                    uploadFileList = createFileList
                )
            )
        } else {
            // 更新：更新视频草稿（含互动配置），支持 fileId 与 uploadId 混合
            val updateFileList = mixedList.mapIndexed { idx, item ->
                UpdateVideoPostCmd.VideoFileInfo(
                    fileId = item.fileId,
                    uploadId = item.uploadId,
                    fileIndex = idx + 1,
                    fileName = item.fileName,
                )
            }
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
                    uploadFileList = updateFileList
                )
            )
        }

        return UCenterPostVideo.Response()
    }

    @PostMapping("/loadVideoList")
    fun getVideoPostPage(request: UCenterLoadVideoList.Request): PageData<UCenterLoadVideoList.VideoItem> {
        val currentUserId = LoginHelper.getUserId()!!

        val queryRequest = GetUserVideoPostQry.Request(
            userId = currentUserId,
            status = if (request.status == -1) null else request.status,
            videoNameFuzzy = request.videoNameFuzzy,
            excludeStatusArray = if (request.status == -1) listOf(4, 5) else null // 进行中排除审核通过和不通过
        ).apply {
            pageNum = request.pageNum
            pageSize = 999
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { video ->
                UCenterLoadVideoList.VideoItem(
                    videoPostId = video.videoPostId,
                    videoId = video.videoId,
                    videoCover = video.videoCover,
                    videoName = video.videoName,
                    duration = video.duration,
                    createTime = video.createTime,
                    lastUpdateTime = video.lastUpdateTime,
                    status = video.status,
                    interaction = video.interaction,
                    playCount = video.playCount,
                    likeCount = video.likeCount,
                    danmuCount = video.danmuCount,
                    commentCount = video.commentCount,
                    coinCount = video.coinCount,
                    collectCount = video.collectCount
                )
            },
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
                status = 4
            )
        ).count

        // 查询审核失败的数量 (status = 5)
        val auditFailCount = Mediator.queries.send(
            GetVideoDraftCountByStatusQry.Request(
                userId = currentUserId,
                status = 5
            )
        ).count

        // 查询进行中的数量 (排除审核通过和不通过)
        val inProgress = Mediator.queries.send(
            GetVideoDraftCountByStatusQry.Request(
                userId = currentUserId,
                excludeStatusArray = listOf(4, 5)
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

        return UCenterGetVideoByVideoId.Response(
            videoInfo = UCenterGetVideoByVideoId.VideoInfo(
                videoId = queryResult.videoInfo.videoId.toString(),
                videoCover = queryResult.videoInfo.videoCover,
                videoName = queryResult.videoInfo.videoName,
                parentCategoryId = queryResult.videoInfo.parentCategoryId,
                categoryId = queryResult.videoInfo.categoryId,
                postType = queryResult.videoInfo.postType,
                originInfo = queryResult.videoInfo.originInfo,
                tags = queryResult.videoInfo.tags,
                introduction = queryResult.videoInfo.introduction,
                interaction = queryResult.videoInfo.interaction,
                status = queryResult.videoInfo.status
            ),
            videoInfoFileList = queryResult.videoFileList.map { file ->
                UCenterGetVideoByVideoId.VideoFileItem(
                    fileId = file.fileId.toString(),
                    uploadId = file.uploadId,
                    fileIndex = file.fileIndex,
                    fileName = file.fileName,
                    fileSize = file.fileSize,
                    filePath = file.filePath,
                    duration = file.duration,
                    updateType = file.updateType,
                    transferResult = file.transferResult
                )
            }
        )
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

