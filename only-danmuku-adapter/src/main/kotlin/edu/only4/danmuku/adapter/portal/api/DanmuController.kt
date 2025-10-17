package edu.only4.danmuku.adapter.portal.api

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.DanmuLoad
import edu.only4.danmuku.adapter.portal.api.payload.DanmuPost
import edu.only4.danmuku.application.commands.video_danmuku.PostDanmukuCmd
import edu.only4.danmuku.application.queries.video_danmuku.GetDanmukuByFileIdQry
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
 * 视频弹幕控制器
 */
@RestController
@RequestMapping("/danmu")
@Validated
class DanmuController {

    /**
     * 加载弹幕列表
     */
    @PostMapping("/loadDanmu")
    fun danmuLoad(@RequestBody @Validated request: DanmuLoad.Request): List<DanmuLoad.DanmukuItem> {
        // 调用查询获取弹幕列表
        val queryResult = Mediator.queries.send(
            GetDanmukuByFileIdQry.Request(
                fileId = request.fileId.toLong(),
                videoId = request.videoId.toLong()
            )
        )

        // 转换为前端需要的格式
        return queryResult.map { danmuku ->
            DanmuLoad.DanmukuItem(
                danmukuId = danmuku.danmukuId.toString(),
                fileId = danmuku.fileId.toString(),
                videoId = danmuku.videoId.toString(),
                userId = danmuku.userId.toString(),
                text = danmuku.text,
                mode = danmuku.mode,
                color = danmuku.color,
                time = danmuku.time,
                postTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(danmuku.postTime),
                    ZoneId.systemDefault()
                ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            )
        }
    }

    /**
     * 发送弹幕
     */
    @PostMapping("/postDanmu")
    fun danmuPost(@RequestBody @Validated request: DanmuPost.Request): DanmuPost.Response {
        // 调用命令发送弹幕
        Mediator.commands.send(
            PostDanmukuCmd.Request(
                videoId = request.videoId.toLong(),
                fileId = request.fileId.toLong(),
                text = request.text,
                mode = request.mode ?: 1, // 默认滚动模式
                color = request.color,
                time = request.time ?: 0 // 默认0秒
            )
        )
        return DanmuPost.Response()
    }

}
