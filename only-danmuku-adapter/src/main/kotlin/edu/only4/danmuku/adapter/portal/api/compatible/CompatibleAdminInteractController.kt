package edu.only4.danmuku.adapter.portal.api.compatible

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.AdminInteractDelComment
import edu.only4.danmuku.adapter.portal.api.payload.AdminInteractDelDanmuku
import edu.only4.danmuku.adapter.portal.api.payload.AdminInteractLoadComment
import edu.only4.danmuku.adapter.portal.api.payload.AdminInteractLoadDanmuku
import edu.only4.danmuku.application.commands.video_comment.DelCommentCmd
import edu.only4.danmuku.application.commands.video_danmuku.DeleteDanmukuCmd
import edu.only4.danmuku.application.queries.video_comment.VideoCommentPageQry
import edu.only4.danmuku.application.queries.video_danmuku.GetDanmukuPageQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@RestController
@RequestMapping("/admin/interact")
@Validated
class CompatibleAdminInteractController {

    @PostMapping("/loadDanmu")
    fun adminInteractLoadDanmuku(@RequestBody request: AdminInteractLoadDanmuku.Request): PageData<AdminInteractLoadDanmuku.Response> {
        // 调用查询获取弹幕分页列表
        val queryRequest = GetDanmukuPageQry.Request(
            videoNameFuzzy = request.videoNameFuzzy
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
            sort.addAll(request.sort)
        }


        val queryResult = Mediator.queries.send(queryRequest)

        // 转换为前端需要的格式
        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { danmuku ->
                AdminInteractLoadDanmuku.Response(
                    danmukuId = danmuku.danmukuId,
                    videoId = danmuku.videoId.toString(),
                    videoName = danmuku.videoName,
                    videoCover = danmuku.videoCover,
                    userId = danmuku.customerId.toString(),
                    nickName = danmuku.customerNickname,
                    text = danmuku.text,
                    mode = danmuku.mode,
                    color = danmuku.color,
                    time = danmuku.time,
                    postTime = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(danmuku.postTime),
                        ZoneId.systemDefault()
                    )
                )
            },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/delDanmu")
    fun adminInteractDelDanmuku(danmuId: Long): AdminInteractDelDanmuku.Response {
        Mediator.commands.send(
            DeleteDanmukuCmd.Request(
                danmukuId = danmuId
            )
        )
        return AdminInteractDelDanmuku.Response()
    }

    @PostMapping("/loadComment")
    fun adminInteractLoadComment(@RequestBody request: AdminInteractLoadComment.Request): PageData<AdminInteractLoadComment.Response> {
        // 调用查询获取评论分页列表
        val queryRequest = VideoCommentPageQry.Request(
            videoNameFuzzy = request.videoNameFuzzy
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
            sort.addAll(request.sort)
        }


        val queryResult = Mediator.queries.send(queryRequest)

        // 转换为前端需要的格式
        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { comment ->
                AdminInteractLoadComment.Response(
                    commentId = comment.commentId,
                    pCommentId = comment.parentCommentId,
                    videoId = comment.videoId.toString(),
                    videoName = comment.videoName,
                    videoCover = comment.videoCover,
                    userId = comment.customerId.toString(),
                    nickName = comment.customerNickname,
                    avatar = comment.customerAvatar,
                    replyUserId = comment.replyCustomerId?.toString(),
                    replyNickName = comment.replyCustomerNickname,
                    content = comment.content,
                    imgPath = comment.imgPath,
                    topType = comment.topType?.toInt(),
                    likeCount = comment.likeCount,
                    hateCount = comment.hateCount,
                    postTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(comment.postTime),
                        ZoneId.systemDefault()
                    )
                )
            },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/delComment")
    fun adminInteractDelComment(commentId: Long): AdminInteractDelComment.Response {
        Mediator.commands.send(
            DelCommentCmd.Request(
                commentId = commentId,
            )
        )
        return AdminInteractDelComment.Response()
    }

}
