package edu.only4.danmuku.adapter.portal.api

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.AdminVideoAudit
import edu.only4.danmuku.adapter.portal.api.payload.AdminVideoDelete
import edu.only4.danmuku.adapter.portal.api.payload.AdminVideoLoadPList
import edu.only4.danmuku.adapter.portal.api.payload.AdminVideoRecommend
import edu.only4.danmuku.application.commands.video.DeleteVideoCmd
import edu.only4.danmuku.application.commands.video.RecommendVideoCmd
import edu.only4.danmuku.application.commands.video_draft.AuditVideoCmd
import edu.only4.danmuku.application.queries.video.GetVideoPlayFilesQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员视频管理控制器
 */
@RestController
@RequestMapping("/admin/videoInfo/v2")
@Validated
class AdminVideoController {

    @PostMapping("/recommendVideo")
    fun adminVideoRecommend(@RequestBody @Validated request: AdminVideoRecommend.Request): AdminVideoRecommend.Response {
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
    fun adminVideoLoadPList(@RequestBody @Validated request: AdminVideoLoadPList.Request): List<AdminVideoLoadPList.Response> {
        // 调用查询获取视频文件列表
        val queryResultList = Mediator.queries.send(
            GetVideoPlayFilesQry.Request(
                videoId = request.videoId!!.toLong()
            )
        )

        // 转换为前端需要的格式
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
