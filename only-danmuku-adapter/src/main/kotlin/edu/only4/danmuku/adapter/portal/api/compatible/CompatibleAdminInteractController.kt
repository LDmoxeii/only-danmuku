package edu.only4.danmuku.adapter.portal.api.compatible

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.AdminInteractLoadComment
import edu.only4.danmuku.adapter.portal.api.payload.AdminInteractLoadDanmuku
import edu.only4.danmuku.application.commands.video_comment.DeleteVideoCommentCmd
import edu.only4.danmuku.application.commands.video_danmuku.DeleteVideoDanmukuCmd
import edu.only4.danmuku.application.queries.video_comment.VideoCommentPageQry
import edu.only4.danmuku.application.queries.video_danmuku.GetVideoDanmukuPageQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/interact")
@Validated
class CompatibleAdminInteractController {

    @PostMapping("/loadDanmu")
    fun getDanmukuPage(
        request: AdminInteractLoadDanmuku.Request,
    ): PageData<AdminInteractLoadDanmuku.Response> {
        val queryRequest = GetVideoDanmukuPageQry.Request(
            videoNameFuzzy = request.videoNameFuzzy
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
            sort.addAll(request.sort)
        }

        val queryResult = Mediator.queries.send(queryRequest)

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
                    // postTime 原为毫秒，换算为秒级时间戳，交由翻译器格式化
                    postTime = danmuku.postTime / 1000
                )
            },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/delDanmu")
    fun deleteDanmuku(danmukuId: Long) {
        Mediator.commands.send(
            DeleteVideoDanmukuCmd.Request(
                danmukuId = danmukuId
            )
        )
    }

    @PostMapping("/loadComment")
    fun getVideoCommentPage(
        request: AdminInteractLoadComment.Request,
    ): PageData<AdminInteractLoadComment.Response> {
        val queryRequest = VideoCommentPageQry.Request(
            videoNameFuzzy = request.videoNameFuzzy
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
            sort.addAll(request.sort)
        }

        val queryResult = Mediator.queries.send(queryRequest)

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
                    topType = comment.topType,
                    likeCount = comment.likeCount,
                    hateCount = comment.hateCount,
                    // 直接返回秒级时间戳，交由翻译器格式化
                    postTime = comment.postTime
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
            )
        )
    }

}
