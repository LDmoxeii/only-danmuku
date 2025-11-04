package edu.only4.danmuku.adapter.portal.api.compatible

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.AdminInteractLoadComment
import edu.only4.danmuku.adapter.portal.api.payload.AdminInteractLoadDanmuku
import edu.only4.danmuku.application.commands.video_comment.DeleteVideoCommentCmd
import edu.only4.danmuku.application.commands.video_danmuku.DeleteVideoDanmukuCmd
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
        val queryRequest = AdminInteractLoadDanmuku.Converter.INSTANCE.toQry(request)

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { AdminInteractLoadDanmuku.Converter.INSTANCE.fromApp(it) },
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
        val queryRequest = AdminInteractLoadComment.Converter.INSTANCE.toQry(request)

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { AdminInteractLoadComment.Converter.INSTANCE.fromApp(it) },
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
