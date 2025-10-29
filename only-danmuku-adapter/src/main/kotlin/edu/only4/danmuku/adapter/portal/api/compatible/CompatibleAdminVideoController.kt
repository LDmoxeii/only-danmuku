package edu.only4.danmuku.adapter.portal.api.compatible

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.AdminVideoLoadList
import edu.only4.danmuku.adapter.portal.api.payload.AdminVideoLoadPList
import edu.only4.danmuku.application.commands.video_draft.DeleteVideoPostCmd
import edu.only4.danmuku.application.commands.video.RecommendVideoCmd
import edu.only4.danmuku.application.commands.video_draft.AuditVideoCmd
import edu.only4.danmuku.application.queries.video.GetVideoPlayFilesQry
import edu.only4.danmuku.application.queries.video_draft.GetVideoPostPageQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * 管理员视频管理控制器
 */
@RestController
@RequestMapping("/admin/videoInfo")
@Validated
class CompatibleAdminVideoController {

    @PostMapping("/loadVideoList")
    fun getVideoPage(request: AdminVideoLoadList.Request): PageData<AdminVideoLoadList.VideoItem> {
        val queryRequest = GetVideoPostPageQry.Request(
            videoNameFuzzy = request.videoNameFuzzy,
            categoryParentId = request.categoryParentId,
            categoryId = request.categoryId,
            recommendType = request.recommendType
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { video ->
                AdminVideoLoadList.VideoItem(
                    videoId = video.videoId.toString(),
                    videoCover = video.videoCover,
                    videoName = video.videoName,
                    userId = video.userId.toString(),
                    nickName = video.nickName,
                    duration = video.duration,
                    status = video.status,
                    createTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(video.createTime),
                        ZoneId.systemDefault()
                    ),
                    lastUpdateTime = video.lastUpdateTime?.let {
                        LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(it),
                            ZoneId.systemDefault()
                        )
                    },
                    playCount = video.playCount,
                    likeCount = video.likeCount,
                    danmuCount = video.danmuCount,
                    commentCount = video.commentCount,
                    coinCount = video.coinCount,
                    collectCount = video.collectCount,
                    recommendType = video.recommendType
                )
            },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/recommendVideo")
    fun recommendVideo(videoId: Long) {
        Mediator.commands.send(
            RecommendVideoCmd.Request(
                videoId = videoId
            )
        )
    }

    @PostMapping("/auditVideo")
    fun auditVideo(
        videoId: Long,
        status: Int,
        reason: String?,
    ) {
        Mediator.commands.send(
            AuditVideoCmd.Request(
                videoId = videoId,
                status = status,
                reason = reason
            )
        )
    }

    @PostMapping("/deleteVideo")
    fun deleteVideo(videoId: Long) {
        Mediator.commands.send(
            DeleteVideoPostCmd.Request(
                videoId = videoId
            )
        )
    }

    @PostMapping("/loadVideoPList")
    fun getVideoPList(videoId: Long): List<AdminVideoLoadPList.Response> {
        val queryResultList = Mediator.queries.send(
            GetVideoPlayFilesQry.Request(
                videoId = videoId
            )
        )

        return queryResultList.map { file ->
            AdminVideoLoadPList.Response(
                fileId = file.fileId.toString(),
                videoId = file.videoId.toString(),
                fileIndex = file.fileIndex,
                fileName = file.fileName,
                fileSize = file.fileSize,
                filePath = file.filePath,
                duration = file.duration
            )
        }
    }

}
