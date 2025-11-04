package edu.only4.danmuku.adapter.portal.api.compatible

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.AdminVideoLoadList
import edu.only4.danmuku.adapter.portal.api.payload.AdminVideoLoadPList
import edu.only4.danmuku.application.commands.video.RecommendVideoCmd
import edu.only4.danmuku.application.commands.video_post.AuditVideoPostCmd
import edu.only4.danmuku.application.commands.video_post.DeleteVideoPostCmd
import edu.only4.danmuku.application.queries.video.GetVideoPlayFilesQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员视频管理控制器
 */
@RestController
@RequestMapping("/admin/videoInfo")
@Validated
class CompatibleAdminVideoController {

    @PostMapping("/loadVideoList")
    fun getVideoPage(request: AdminVideoLoadList.Request): PageData<AdminVideoLoadList.VideoItem> {
        val queryRequest = AdminVideoLoadList.Converter.INSTANCE.toQry(request)

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { AdminVideoLoadList.Converter.INSTANCE.fromApp(it) },
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
            AuditVideoPostCmd.Request(
                videoPostId = videoId,
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

        return queryResultList.map { AdminVideoLoadPList.Converter.INSTANCE.fromApp(it) }
    }

}
