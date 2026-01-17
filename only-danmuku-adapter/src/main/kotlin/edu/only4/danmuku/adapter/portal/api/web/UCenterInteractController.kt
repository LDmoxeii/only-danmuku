package edu.only4.danmuku.adapter.portal.api.web

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.u_center_interact.DeleteComment
import edu.only4.danmuku.adapter.portal.api.payload.u_center_interact.DeleteDanmuku
import edu.only4.danmuku.adapter.portal.api.payload.u_center_interact.GetAllVideoList
import edu.only4.danmuku.adapter.portal.api.payload.u_center_interact.GetCommentPage
import edu.only4.danmuku.adapter.portal.api.payload.u_center_interact.GetDanmukuPage
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

@RestController
@RequestMapping("/uCenter")
class UCenterInteractController {

    @PostMapping("/getAllVideoList")
    fun getAllVideoList(): List<GetAllVideoList.Item> {
        val currentUserId = LoginHelper.getUserId()!!

        // 调用查询获取当前用户的所有视频
        val queryResult = Mediator.queries.send(
            GetVideoAllList.Request(
                userId = currentUserId,
            )
        )

        // 转换为前端需要的格式
        return queryResult.map { GetAllVideoList.Converter.INSTANCE.fromApp(it) }
    }

    @PostMapping("/getCommentPage")
    fun getCommentPage(@RequestBody @Validated request: GetCommentPage.Request): PageData<GetCommentPage.Item> {
        val currentUserId = LoginHelper.getUserId()!!

        val queryResult = Mediator.queries.send(
            GetCommentPage.Converter.INSTANCE.toQry(request, currentUserId)
        )

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { GetCommentPage.Converter.INSTANCE.fromQry(it) },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/delComment")
    fun deleteComment(@RequestBody @Validated request: DeleteComment.Request) =
        Mediator.commands.send(
            DeleteVideoCommentCmd.Request(
                commentId = request.commentId,
                operatorId = LoginHelper.getUserId()!!
            )
        )

    @PostMapping("/getDanmukuPage")
    fun getDanmukuPage(@RequestBody @Validated request: GetDanmukuPage.Request): PageData<GetDanmukuPage.DanmukuItem> {
        val currentUserId = LoginHelper.getUserId()!!

        val queryResult = Mediator.queries.send(
            GetDanmukuPage.Converter.INSTANCE.toQry(request, currentUserId)
        )

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { GetDanmukuPage.Converter.INSTANCE.fromApp(it) },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/deleteDanmuku")
    fun deleteDanmuku(@RequestBody @Validated request: DeleteDanmuku.Request) =
        Mediator.commands.send(
            DeleteVideoDanmukuCmd.Request(
                danmukuId = request.danmukuId,
                operatorId = LoginHelper.getUserId()!!
            )
        )
}
