package edu.only4.danmuku.adapter.portal.api.compatible

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.AdminVideoLoadList
import edu.only4.danmuku.adapter.portal.api.payload.AdminVideoLoadPList
import edu.only4.danmuku.application.commands.video.RecommendVideoCmd
import edu.only4.danmuku.application.commands.video_post.DeleteVideoPostCmd
import edu.only4.danmuku.application.commands.video_post.RecordVideoAuditTraceCmd
import edu.only4.danmuku.application.queries.video.GetVideoPlayFilesQry
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.video_audit_trace.enums.AuditStatus
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
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
        val currentUserId = LoginHelper.getUserId()!!
        val currentUserType = UserType.valueOf(LoginHelper.getUserInfo()!!.userType)
        val auditStatus = when (status) {
            VideoStatus.REVIEW_PASSED.code -> AuditStatus.PASSED
            VideoStatus.REVIEW_FAILED.code -> AuditStatus.FAILED
            else -> throw IllegalArgumentException("不支持的审核状态: $status")
        }
        Mediator.commands.send(
            RecordVideoAuditTraceCmd.Request(
                videoPostId = videoId,
                auditStatus = auditStatus,
                reviewerId = currentUserId,
                reviewerType = currentUserType,
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
