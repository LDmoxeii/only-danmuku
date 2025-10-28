package edu.only4.danmuku.adapter.portal.api.compatible

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.UCenterLoadAllVideo
import edu.only4.danmuku.adapter.portal.api.payload.UCenterLoadComment
import edu.only4.danmuku.adapter.portal.api.payload.UCenterLoadDanmu
import edu.only4.danmuku.application.commands.video_comment.DeleteVideoCommentCmd
import edu.only4.danmuku.application.commands.video_danmuku.DeleteVideoDanmukuCmd
import edu.only4.danmuku.application.queries.video.GetVideoAllList
import edu.only4.danmuku.application.queries.video_comment.VideoCommentPageQry
import edu.only4.danmuku.application.queries.video_danmuku.GetVideoDanmukuPageQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/ucenter")
@Validated
class CompatibleUCenterInteractController {

    @PostMapping("/loadAllVideo")
    fun getVideoAllList(): List<UCenterLoadAllVideo.VideoItem> {
        val currentUserId = LoginHelper.getUserId()!!

        // 调用查询获取当前用户的所有视频
        val queryResult = Mediator.queries.send(
            GetVideoAllList.Request(
                userId = currentUserId,
            )
        )

        // 转换为前端需要的格式
        return queryResult.map { video ->
            UCenterLoadAllVideo.VideoItem(
                videoId = video.videoId.toString(),
                videoCover = video.videoCover,
                videoName = video.videoName,
                createTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(video.createTime),
                    ZoneId.systemDefault()
                ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            )
        }
    }

    @PostMapping("/loadComment")
    fun getUserCommentPage(@RequestBody request: UCenterLoadComment.Request): PageData<UCenterLoadComment.CommentItem> {
        val currentUserId = LoginHelper.getUserId()!!

        val queryRequest = VideoCommentPageQry.Request(
            videoUserId = currentUserId,
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { comment ->
                UCenterLoadComment.CommentItem(
                    commentId = comment.commentId.toString(),
                    videoId = comment.videoId.toString(),
                    videoName = comment.videoName,
                    content = comment.content,
                    userId = comment.customerId.toString(),
                    nickName = comment.customerNickname,
                    postTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(comment.postTime),
                        ZoneId.systemDefault()
                    ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
            },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/delComment")
    fun deleteVideoComment(commentId: Long) {
        Mediator.commands.send(
            DeleteVideoCommentCmd.Request(
                commentId = commentId,
                operatorId = LoginHelper.getUserId()!!
            )
        )
    }

    @PostMapping("/loadDanmu")
    fun getVideoDanmukuPage(@RequestBody request: UCenterLoadDanmu.Request): PageData<UCenterLoadDanmu.DanmukuItem> {
        val currentUserId = LoginHelper.getUserId()!!

        val queryRequest = GetVideoDanmukuPageQry.Request(
            videoUserId = currentUserId,
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { danmuku ->
                UCenterLoadDanmu.DanmukuItem(
                    danmukuId = danmuku.danmukuId.toString(),
                    videoId = danmuku.videoId.toString(),
                    videoName = danmuku.videoName,
                    text = danmuku.text,
                    userId = danmuku.customerId.toString(),
                    mode = danmuku.mode,
                    color = danmuku.color,
                    time = danmuku.time,
                    postTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(danmuku.postTime),
                        ZoneId.systemDefault()
                    ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
            },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/delDanmu")
    fun deleteVideoDanmuku(danmuId: Long) {
        Mediator.commands.send(
            DeleteVideoDanmukuCmd.Request(
                danmukuId = danmuId,
                operatorId = LoginHelper.getUserId()!!
            )
        )
    }

}
