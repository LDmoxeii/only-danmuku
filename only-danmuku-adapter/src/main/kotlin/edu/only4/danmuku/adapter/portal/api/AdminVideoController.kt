package edu.only4.danmuku.adapter.portal.api

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.*
import edu.only4.danmuku.application.commands.video.DeleteVideoCmd
import edu.only4.danmuku.application.commands.video.RecommendVideoCmd
import edu.only4.danmuku.application.commands.video_draft.AuditVideoCmd
import edu.only4.danmuku.application.queries.video.GetVideoPlayFilesQry
import edu.only4.danmuku.application.queries.video.SearchVideosQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
class AdminVideoController {

    /**
     * 加载视频列表(分页)
     */
    @PostMapping("/loadVideoList")
    fun adminVideoLoadList(@RequestBody request: AdminVideoLoadList.Request): PageData<AdminVideoLoadList.VideoItem> {
        // 调用查询获取视频分页列表
        val queryRequest = SearchVideosQry.Request(
            videoNameFuzzy = request.videoNameFuzzy,
            status = request.status
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
                AdminVideoLoadList.VideoItem(
                    videoId = video.videoId.toString(),
                    videoCover = video.videoCover,
                    videoName = video.videoName,
                    userId = video.userId.toString(),
                    nickName = video.nickName,
                    duration = video.duration,
                    status = video.status,
                    createTime = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(video.createTime),
                        ZoneId.systemDefault()
                    ),
                    lastUpdateTime = video.lastUpdateTime?.let {
                        LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(it),
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

    /**
     * 推荐视频
     */
    @PostMapping("/recommendVideo")
    fun adminVideoRecommend(@RequestBody @Validated request: AdminVideoRecommend.Request): AdminVideoRecommend.Response {
        // 调用命令推荐视频
        Mediator.commands.send(
            RecommendVideoCmd.Request(
                videoId = request.videoId!!.toLong()
            )
        )
        return AdminVideoRecommend.Response()
    }

    /**
     * 审核视频
     */
    @PostMapping("/auditVideo")
    fun adminVideoAudit(@RequestBody @Validated request: AdminVideoAudit.Request): AdminVideoAudit.Response {
        // 调用命令审核视频
        Mediator.commands.send(
            AuditVideoCmd.Request(
                videoId = request.videoId!!.toLong(),
                status = request.status!!,
                reason = request.reason
            )
        )
        return AdminVideoAudit.Response()
    }

    /**
     * 删除视频
     */
    @PostMapping("/deleteVideo")
    fun adminVideoDelete(@RequestBody @Validated request: AdminVideoDelete.Request): AdminVideoDelete.Response {
        // 调用命令删除视频
        Mediator.commands.send(
            DeleteVideoCmd.Request(
                videoId = request.videoId!!.toLong()
            )
        )
        return AdminVideoDelete.Response()
    }

    /**
     * 加载视频分片列表
     */
    @PostMapping("/loadVideoPList")
    fun adminVideoLoadPList(@RequestBody @Validated request: AdminVideoLoadPList.Request): List<AdminVideoLoadPList.FileItem> {
        // 调用查询获取视频文件列表
        val queryResult = Mediator.queries.send(
            GetVideoPlayFilesQry.Request(
                videoId = request.videoId!!.toLong()
            )
        )

        // 转换为前端需要的格式
        return queryResult.list.map { file ->
            AdminVideoLoadPList.FileItem(
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
