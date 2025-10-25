package edu.only4.danmuku.adapter.portal.api

import com.only.engine.json.misc.JsonUtils
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.*
import edu.only4.danmuku.application.commands.video.ChangeVideoInteractionCmd
import edu.only4.danmuku.application.commands.video.DeleteVideoCmd
import edu.only4.danmuku.application.commands.video_draft.SaveVideoInfoCmd
import edu.only4.danmuku.application.queries.video_draft.GetUserVideoDraftsQry
import edu.only4.danmuku.application.queries.video_draft.GetVideoDraftCountByStatusQry
import edu.only4.danmuku.application.queries.video_draft.GetVideoDraftInfoQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * 用户中心-视频发布控制器
 * 处理视频上传、编辑、删除等操作
 */
@RestController
@RequestMapping("/ucenter")
@Validated
class UCenterVideoPostController {

    /**
     * 发布视频
     */
    @PostMapping("/postVideo")
    fun postVideo(@RequestBody @Validated request: UCenterPostVideo.Request): UCenterPostVideo.Response {
        // 解析上传文件列表 JSON
        val fileList = JsonUtils.parseArray(request.uploadFileList, SaveVideoInfoCmd.VideoFileInfo::class.java)

        // 调用命令保存视频信息
        val currentUserId = LoginHelper.getUserId()!!
        Mediator.commands.send(
            SaveVideoInfoCmd.Request(
                customerId = currentUserId,
                videoId = request.videoId?.toLong(),
                videoCover = request.videoCover,
                videoName = request.videoName,
                pCategoryId = request.pCategoryId,
                categoryId = request.categoryId,
                postType = request.postType,
                tags = request.tags,
                introduction = request.introduction,
                interaction = request.interaction,
                uploadFileList = fileList
            )
        )

        return UCenterPostVideo.Response()
    }

    /**
     * 加载用户发布的视频列表
     */
    @PostMapping("/loadVideoList")
    fun loadVideoList(@RequestBody request: UCenterLoadVideoList.Request): PageData<UCenterLoadVideoList.VideoItem> {
        val currentUserId = LoginHelper.getUserId()!!

        // 构建查询请求
        val queryRequest = GetUserVideoDraftsQry.Request(
            userId = currentUserId,
            status = request.status,
            videoNameFuzzy = request.videoNameFuzzy,
            excludeStatusArray = if (request.status == -1) listOf(3, 4) else null // 进行中排除审核通过和不通过
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        // 转换为前端需要的格式
        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { video ->
                UCenterLoadVideoList.VideoItem(
                    videoId = video.videoId.toString(),
                    videoCover = video.videoCover,
                    videoName = video.videoName,
                    createTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(video.createTime),
                        ZoneId.systemDefault()
                    ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    lastUpdateTime = video.lastUpdateTime?.let {
                        LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(it),
                            ZoneId.systemDefault()
                        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    },
                    status = video.status,
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

    /**
     * 获取视频统计信息
     */
    @GetMapping("/getVideoCountInfo")
    fun getVideoCountInfo(): UCenterGetVideoCountInfo.Response {
        val currentUserId = LoginHelper.getUserId()!!

        // 查询审核通过的数量 (status = 3)
        val auditPassCount = Mediator.queries.send(
            GetVideoDraftCountByStatusQry.Request(
                userId = currentUserId,
                status = 3
            )
        ).count

        // 查询审核失败的数量 (status = 4)
        val auditFailCount = Mediator.queries.send(
            GetVideoDraftCountByStatusQry.Request(
                userId = currentUserId,
                status = 4
            )
        ).count

        // 查询进行中的数量 (排除审核通过和不通过)
        val inProgress = Mediator.queries.send(
            GetVideoDraftCountByStatusQry.Request(
                userId = currentUserId,
                excludeStatusArray = listOf(3, 4)
            )
        ).count

        return UCenterGetVideoCountInfo.Response(
            auditPassCount = auditPassCount,
            auditFailCount = auditFailCount,
            inProgress = inProgress
        )
    }

    /**
     * 获取视频编辑信息
     */
    @PostMapping("/getVideoByVideoId")
    fun getVideoByVideoId(@RequestBody @Validated request: UCenterGetVideoByVideoId.Request): UCenterGetVideoByVideoId.Response {
        val currentUserId = LoginHelper.getUserId()!!

        // 调用查询获取视频信息
        val queryResult = Mediator.queries.send(
            GetVideoDraftInfoQry.Request(
                videoId = request.videoId.toLong(),
                userId = currentUserId
            )
        )

        // 转换为前端需要的格式
        return UCenterGetVideoByVideoId.Response(
            videoInfo = UCenterGetVideoByVideoId.VideoInfo(
                videoId = queryResult.videoInfo.videoId.toString(),
                videoCover = queryResult.videoInfo.videoCover,
                videoName = queryResult.videoInfo.videoName,
                pCategoryId = queryResult.videoInfo.pCategoryId,
                categoryId = queryResult.videoInfo.categoryId,
                postType = queryResult.videoInfo.postType,
                tags = queryResult.videoInfo.tags,
                introduction = queryResult.videoInfo.introduction,
                interaction = queryResult.videoInfo.interaction,
                status = queryResult.videoInfo.status
            ),
            videoFileList = queryResult.videoFileList.map { file ->
                UCenterGetVideoByVideoId.VideoFileItem(
                    fileId = file.fileId.toString(),
                    uploadId = file.uploadId,
                    fileIndex = file.fileIndex,
                    fileName = file.fileName,
                    fileSize = file.fileSize,
                    filePath = file.filePath,
                    duration = file.duration
                )
            }
        )
    }

    /**
     * 保存视频互动设置
     */
    @PostMapping("/saveVideoInteraction")
    fun saveVideoInteraction(@RequestBody @Validated request: UCenterSaveVideoInteraction.Request): UCenterSaveVideoInteraction.Response {
        val userId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            ChangeVideoInteractionCmd.Request(
                videoId = request.videoId.toLong(),
                userId = userId,
                interaction = request.interaction
            )
        )

        return UCenterSaveVideoInteraction.Response()
    }

    /**
     * 删除视频
     */
    @PostMapping("/deleteVideo")
    fun deleteVideo(@RequestBody @Validated request: UCenterDeleteVideo.Request): UCenterDeleteVideo.Response {
        // 调用命令删除视频
        val currentUserId = LoginHelper.getUserId()!!
        Mediator.commands.send(
            DeleteVideoCmd.Request(
                videoId = request.videoId.toLong(),
                operatorId = currentUserId
            )
        )

        return UCenterDeleteVideo.Response()
    }

}

