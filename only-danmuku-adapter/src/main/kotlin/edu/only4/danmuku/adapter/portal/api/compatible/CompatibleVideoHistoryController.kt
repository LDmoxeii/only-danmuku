package edu.only4.danmuku.adapter.portal.api.compatible

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.HistoryClean
import edu.only4.danmuku.adapter.portal.api.payload.HistoryDel
import edu.only4.danmuku.adapter.portal.api.payload.HistoryLoad
import edu.only4.danmuku.application.commands.video_play_history.ClearHistoryCmd
import edu.only4.danmuku.application.commands.video_play_history.DelHistoryCmd
import edu.only4.danmuku.application.queries.video_play_history.GetUserPlayHistoryQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * 播放历史控制器
 */
@RestController
@RequestMapping("/history")
@Validated
class CompatibleVideoHistoryController {

    /**
     * 加载播放历史
     */
    @PostMapping("/loadHistory")
    fun historyLoad(@RequestBody request: HistoryLoad.Request): PageData<HistoryLoad.Response> {
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
            list = queryResult.list.map { history ->
                HistoryLoad.Response(
                    historyId = history.historyId.toString(),
                    videoId = history.videoId.toString(),
                    videoName = history.videoName,
                    videoCover = history.videoCover,
                    fileIndex = history.fileIndex,
                    playTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(history.playTime),
                        ZoneId.systemDefault()
                    ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
            },
            totalCount = queryResult.totalCount
        )
    }

    /**
     * 清空播放历史
     */
    @PostMapping("/cleanHistory")
    fun historyClean(): HistoryClean.Response {
        val currentUserId = LoginHelper.getUserId()!!
        // 调用命令清空当前用户的播放历史
        Mediator.commands.send(ClearHistoryCmd.Request(customerId = currentUserId))
        return HistoryClean.Response()
    }

    /**
     * 删除指定播放历史
     */
    @PostMapping("/delHistory")
    fun historyDel(videoId: Long): HistoryDel.Response {
        val currentUserId = LoginHelper.getUserId()!!
        // 调用命令删除当前用户该视频的播放历史
        Mediator.commands.send(
            DelHistoryCmd.Request(
                customerId = currentUserId,
                videoId = videoId
            )
        )
        return HistoryDel.Response()
    }

}
