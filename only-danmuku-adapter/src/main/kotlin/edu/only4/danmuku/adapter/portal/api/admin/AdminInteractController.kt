package edu.only4.danmuku.adapter.portal.api.admin

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.admin_interact.DeleteComment
import edu.only4.danmuku.adapter.portal.api.payload.admin_interact.DeleteDanmuku
import edu.only4.danmuku.adapter.portal.api.payload.admin_interact.GetVideoCommentPage
import edu.only4.danmuku.adapter.portal.api.payload.admin_interact.GetDanmukuPage
import edu.only4.danmuku.application.commands.video_comment.DeleteVideoCommentCmd
import edu.only4.danmuku.application.commands.video_danmuku.DeleteVideoDanmukuCmd
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/interact")
class AdminInteractController {

    @PostMapping("/page")
    fun page(@RequestBody @Validated request: GetDanmukuPage.Request): PageData<GetDanmukuPage.Response> {
        val queryRequest = GetDanmukuPage.Converter.INSTANCE.toQry(request)

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { GetDanmukuPage.Converter.INSTANCE.fromApp(it) },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/delDanmuku")
    fun deleteDanmuku(@RequestBody @Validated request: DeleteDanmuku.Request) {
        Mediator.commands.send(
            DeleteVideoDanmukuCmd.Request(
                danmukuId = request.danmukuId
            )
        )
    }

    @PostMapping("/getVideoCommentPage")
    fun getVideoCommentPage(@RequestBody @Validated request: GetVideoCommentPage.Request): PageData<GetVideoCommentPage.Response> {
        val queryRequest = GetVideoCommentPage.Converter.INSTANCE.toQry(request)

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { GetVideoCommentPage.Converter.INSTANCE.fromApp(it) },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/delComment")
    fun deleteComment(@RequestBody @Validated request: DeleteComment.Request) {
        Mediator.commands.send(
            DeleteVideoCommentCmd.Request(
                commentId = request.commentId,
            )
        )
    }

}
