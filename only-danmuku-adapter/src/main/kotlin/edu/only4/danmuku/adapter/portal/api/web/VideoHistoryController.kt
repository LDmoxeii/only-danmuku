package edu.only4.danmuku.adapter.portal.api.web

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.video_history.DeleteVideoHistory
import edu.only4.danmuku.adapter.portal.api.payload.video_history.GetHistoryPage
import edu.only4.danmuku.application.commands.video_play_history.ClearHistoryCmd
import edu.only4.danmuku.application.commands.video_play_history.DelHistoryCmd
import edu.only4.danmuku.application.queries.video_play_history.GetUserPlayHistoryQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 播放历史控制器
 */
@RestController
@RequestMapping("/history")
class VideoHistoryController {

    /**
     * 加载播放历史
     */
    @PostMapping("/page")
    fun page(@RequestBody @Validated request: GetHistoryPage.Request): PageData<GetHistoryPage.Item> {
        val currentUserId = LoginHelper.getUserId()!!

        // 调用查询获取播放历史分页列表
        val queryRequest = GetUserPlayHistoryQry.Request(
            customerId = currentUserId
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        // 转换为前端需要的格式
        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { GetHistoryPage.Converter.INSTANCE.fromApp(it) },
            totalCount = queryResult.totalCount
        )
    }

    /**
     * 清空播放历史
     */
    @PostMapping("/cleanHistory")
    fun historyClean() {
        val currentUserId = LoginHelper.getUserId()!!
        // 调用命令清空当前用户的播放历史
        Mediator.commands.send(ClearHistoryCmd.Request(customerId = currentUserId))
    }

    /**
     * 删除指定播放历史
     */
    @PostMapping("/delete")
    fun delete(@RequestBody @Validated request: DeleteVideoHistory.Request) {
        val currentUserId = LoginHelper.getUserId()!!
        // 调用命令删除当前用户该视频的播放历史
        Mediator.commands.send(
            DelHistoryCmd.Request(
                customerId = currentUserId,
                videoId = request.videoId
            )
        )
    }

}
