package edu.only4.danmuku.adapter.portal.api.web

import com.only.engine.exception.KnownException
import com.only.engine.json.misc.JsonUtils
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.u_center_video_post.DeleteVideo
import edu.only4.danmuku.adapter.portal.api.payload.u_center_video_post.SaveVideoPost
import edu.only4.danmuku.adapter.portal.api.payload.u_center_video_post.SaveVideoPostInteraction
import edu.only4.danmuku.adapter.portal.api.payload.u_center_video_post.GetVideoByVideoId
import edu.only4.danmuku.adapter.portal.api.payload.u_center_video_post.GetVideoPostCountInfo
import edu.only4.danmuku.adapter.portal.api.payload.u_center_video_post.GetVideoPostPage
import edu.only4.danmuku.adapter.portal.api.payload.u_center_video_post.UpdateVideoPost
import edu.only4.danmuku.application.commands.video_post.ChangeVideoPostInteractionCmd
import edu.only4.danmuku.application.commands.video_post.CreateVideoPostCmd
import edu.only4.danmuku.application.commands.video_post.DeleteVideoPostCmd
import edu.only4.danmuku.application.commands.video_post.UpdateVideoPostCmd
import edu.only4.danmuku.application.queries.video_draft.GetUserVideoPostQry
import edu.only4.danmuku.application.queries.video_draft.GetVideoDraftCountByStatusQry
import edu.only4.danmuku.application.queries.video_draft.GetVideoPostInfoQry
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/uCenter")
class UCenterVideoPostController {

    @PostMapping("/videoPost/save")
    fun save(@RequestBody @Validated request: SaveVideoPost.Request): SaveVideoPost.Response {
        val currentUserId = LoginHelper.getUserId()!!
        val seenIndex = mutableSetOf<Int>()
        val uploadFiles = JsonUtils.parseArray(request.uploadFileList, SaveVideoPost.PostFileItem::class.java)
            .mapIndexed { index, item ->
                val fileIndex = item.fileIndex ?: index
                if (!seenIndex.add(fileIndex)) {
                    throw KnownException("文件索引重复: $fileIndex")
                }
                CreateVideoPostCmd.VideoPostFileSpec(
                    uploadId = item.uploadId,
                    fileIndex = fileIndex,
                    fileName = item.fileName,
                    fileSize = item.fileSize,
                    duration = item.duration
                )
            }
        Mediator.commands.send(
            CreateVideoPostCmd.Request(
                customerId = currentUserId,
                videoCover = request.videoCover,
                videoName = request.videoName,
                parentCategoryId = request.parentCategoryId,
                categoryId = request.categoryId,
                postType = request.postType,
                originInfo = request.originInfo,
                tags = request.tags,
                introduction = request.introduction,
                interaction = request.interaction,
                uploadFileList = uploadFiles,
            )
        )
        return SaveVideoPost.Response()
    }

    @PostMapping("/videoPost//update")
    fun update(@RequestBody @Validated request: UpdateVideoPost.Request): UpdateVideoPost.Response {
        val currentUserId = LoginHelper.getUserId()!!
        val seenIndex = mutableSetOf<Int>()
        val uploadFiles = JsonUtils.parseArray(request.uploadFileList, UpdateVideoPost.PostFileItem::class.java)
            .mapIndexed { index, item ->
                val fileIndex = item.fileIndex ?: index
                if (!seenIndex.add(fileIndex)) {
                    throw KnownException("文件索引重复: $fileIndex")
                }
                UpdateVideoPostCmd.VideoPostFileSpec(
                    uploadId = item.uploadId,
                    fileIndex = fileIndex,
                    fileName = item.fileName,
                    fileSize = item.fileSize,
                    duration = item.duration
                )
            }
        Mediator.commands.send(
            UpdateVideoPostCmd.Request(
                videoPostId = request.videoPostId,
                customerId = currentUserId,
                videoCover = request.videoCover,
                videoName = request.videoName,
                pCategoryId = request.parentCategoryId,
                categoryId = request.categoryId,
                postType = request.postType,
                originInfo = request.originInfo,
                tags = request.tags,
                introduction = request.introduction,
                interaction = request.interaction,
                uploadFileList = uploadFiles,
            )
        )
        return UpdateVideoPost.Response()
    }

    @PostMapping("/videoPost/getPage")
    fun getPage(@RequestBody @Validated request: GetVideoPostPage.Request): PageData<GetVideoPostPage.Item> {
        val currentUserId = LoginHelper.getUserId()!!

        val queryRequest = GetUserVideoPostQry.Request(
            userId = currentUserId,
            status = if (request.status == VideoStatus.UNKNOW) null else request.status,
            videoNameFuzzy = request.videoNameFuzzy,
            excludeStatusArray = if (request.status == VideoStatus.UNKNOW) listOf(
                VideoStatus.REVIEW_PASSED,
                VideoStatus.REVIEW_FAILED
            ) else null // 进行中排除审核通过和不通过
        ).apply {
            pageNum = request.pageNum
            pageSize = 999
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { GetVideoPostPage.Converter.INSTANCE.fromApp(it) },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/videoPost/getCountInfo")
    fun getCountInfo(): GetVideoPostCountInfo.Response {
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

        return GetVideoPostCountInfo.Response(
            auditPassCount = auditPassCount,
            auditFailCount = auditFailCount,
            inProgress = inProgress
        )
    }

    @PostMapping("/getVideoByVideoId")
    fun getVideoByVideoId(@RequestBody @Validated request: GetVideoByVideoId.Request): GetVideoByVideoId.Response {
        val currentUserId = LoginHelper.getUserId()!!

        val queryResult = Mediator.queries.send(
            GetVideoPostInfoQry.Request(
                videoPostId = request.videoId,
                userId = currentUserId
            )
        )

        return GetVideoByVideoId.Converter.INSTANCE.fromApp(queryResult)
    }

    @PostMapping("/videoPost/saveInteraction")
    fun saveInteraction(@RequestBody @Validated request: SaveVideoPostInteraction.Request) {
        val userId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            ChangeVideoPostInteractionCmd.Request(
                videoPostId = request.videoPostId,
                userId = userId,
                interaction = request.interaction
            )
        )
    }

    @PostMapping("videoPost/delete")
    fun delete(@RequestBody @Validated request: DeleteVideo.Request) {
        val currentUserId = LoginHelper.getUserId()!!
        Mediator.commands.send(
            DeleteVideoPostCmd.Request(
                videoId = request.videoId,
                operatorId = currentUserId
            )
        )
    }
}

