package edu.only4.danmuku.adapter.portal.api.admin

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.admin_video.GetVideoPage
import edu.only4.danmuku.adapter.portal.api.payload.admin_video.GetVideoPList
import edu.only4.danmuku.adapter.portal.api.payload.admin_video.AuditVideo
import edu.only4.danmuku.adapter.portal.api.payload.admin_video.DeleteVideo
import edu.only4.danmuku.adapter.portal.api.payload.admin_video.RecommendVideo
import edu.only4.danmuku.application.commands.video.RecommendVideoCmd
import edu.only4.danmuku.application.commands.video_post.DeleteVideoPostCmd
import edu.only4.danmuku.application.commands.video_post.RecordVideoAuditTraceCmd
import edu.only4.danmuku.application.queries.video.GetVideoPlayFilesQry
import edu.only4.danmuku.domain.aggregates.user.enums.UserType
import edu.only4.danmuku.domain.aggregates.video_audit_trace.enums.AuditStatus
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员视频管理控制器
 */
@RestController
@RequestMapping("/admin/video")
@Validated
class AdminVideoController {

    @PostMapping("/page")
    fun page(@RequestBody @Validated request: GetVideoPage.Request): PageData<GetVideoPage.Item> {
        val queryRequest = GetVideoPage.Converter.INSTANCE.toQry(request)

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { GetVideoPage.Converter.INSTANCE.fromApp(it) },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/recommendVideo")
    fun recommendVideo(@RequestBody @Validated request: RecommendVideo.Request) {
        Mediator.commands.send(
            RecommendVideoCmd.Request(
                videoId = request.videoId
            )
        )
    }

    @PostMapping("/auditVideo")
    fun auditVideo(@RequestBody @Validated request: AuditVideo.Request) {
        val currentUserId = LoginHelper.getUserId()!!
        val currentUserType = UserType.valueOf(LoginHelper.getUserInfo()!!.userType)
        val auditStatus = when (request.status) {
            VideoStatus.REVIEW_PASSED.code -> AuditStatus.PASSED
            VideoStatus.REVIEW_FAILED.code -> AuditStatus.FAILED
            else -> throw IllegalArgumentException("不支持的审核状态: ${request.status}")
        }
        Mediator.commands.send(
            RecordVideoAuditTraceCmd.Request(
                videoPostId = request.videoId,
                auditStatus = auditStatus,
                reviewerId = currentUserId,
                reviewerType = currentUserType,
                reason = request.reason
            )
        )
    }

    @PostMapping("/deleteVideo")
    fun deleteVideo(@RequestBody @Validated request: DeleteVideo.Request) {
        Mediator.commands.send(
            DeleteVideoPostCmd.Request(
                videoId = request.videoId
            )
        )
    }

    @PostMapping("/getVideoPList")
    fun getVideoPList(@RequestBody @Validated request: GetVideoPList.Request): List<GetVideoPList.Item> {
        val queryResultList = Mediator.queries.send(
            GetVideoPlayFilesQry.Request(
                videoId = request.videoId
            )
        )

        return queryResultList.map { GetVideoPList.Converter.INSTANCE.fromApp(it) }
    }

}
