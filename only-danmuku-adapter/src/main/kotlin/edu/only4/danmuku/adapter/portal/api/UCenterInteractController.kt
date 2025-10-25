package edu.only4.danmuku.adapter.portal.api

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.*
import edu.only4.danmuku.application.commands.video_comment.DelCommentCmd
import edu.only4.danmuku.application.commands.video_danmuku.DeleteDanmukuCmd
import edu.only4.danmuku.application.queries.video.SearchVideosQry
import edu.only4.danmuku.application.queries.video_comment.VideoCommentPageQry
import edu.only4.danmuku.application.queries.video_danmuku.GetDanmukuPageQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * 用户中心-互动管理控制器
 * 处理用户收到的评论、弹幕等互动信息
 */
@RestController
@RequestMapping("/ucenter/v2")
@Validated
class UCenterInteractController {

    /**
     * 加载所有视频
     */
    @GetMapping("/loadAllVideo")
    fun loadAllVideo(): List<UCenterLoadAllVideo.VideoItem> {
        val currentUserId = LoginHelper.getUserId()!!

        // 调用查询获取当前用户的所有视频
        val queryResult = Mediator.queries.send(
            SearchVideosQry.Request(
                userId = currentUserId,
                videoNameFuzzy = null,
                status = null
            )
        )

        // 转换为前端需要的格式
        return queryResult.list.map { video ->
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

    /**
     * 加载用户收到的评论
     */
    @PostMapping("/loadComment")
    fun loadComment(@RequestBody request: UCenterLoadComment.Request): PageData<UCenterLoadComment.CommentItem> {
        val currentUserId = LoginHelper.getUserId()!!

        // 构建查询请求 - 查询视频作者收到的评论
        val queryRequest = VideoCommentPageQry.Request(
            videoUserId = currentUserId, // 过滤当前用户作为视频作者收到的评论
            videoNameFuzzy = null // 可以扩展支持视频名称搜索
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        // 转换为前端需要的格式
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

    /**
     * 删除评论
     */
    @PostMapping("/delComment")
    fun delComment(@RequestBody @Validated request: UCenterDelComment.Request): UCenterDelComment.Response {
        // 调用命令删除评论
        Mediator.commands.send(
            DelCommentCmd.Request(
                commentId = request.commentId.toLong(),
                operatorId = LoginHelper.getUserId()!!
            )
        )

        return UCenterDelComment.Response()
    }

    /**
     * 加载用户收到的弹幕
     */
    @PostMapping("/loadDanmu")
    fun loadDanmu(@RequestBody request: UCenterLoadDanmu.Request): PageData<UCenterLoadDanmu.DanmukuItem> {
        val currentUserId = LoginHelper.getUserId()!!

        // 构建查询请求 - 查询视频作者收到的弹幕
        val queryRequest = GetDanmukuPageQry.Request(
            videoUserId = currentUserId, // 过滤当前用户作为视频作者收到的弹幕
            videoNameFuzzy = null // 可以扩展支持视频名称搜索
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        // 转换为前端需要的格式
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

    /**
     * 删除弹幕
     */
    @PostMapping("/delDanmu")
    fun delDanmu(@RequestBody @Validated request: UCenterDelDanmu.Request): UCenterDelDanmu.Response {
        Mediator.commands.send(
            DeleteDanmukuCmd.Request(
                danmukuId = request.danmuId.toLong(),
                operatorId = LoginHelper.getUserId()!!
            )
        )

        return UCenterDelDanmu.Response()
    }

}
