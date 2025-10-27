package edu.only4.danmuku.adapter.portal.api.compatible

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.DanmukuLoad
import edu.only4.danmuku.adapter.portal.api.payload.DanmukuPost
import edu.only4.danmuku.application.commands.video_danmuku.PostDanmukuCmd
import edu.only4.danmuku.application.queries.video_danmuku.GetDanmukuByFileIdQry
import io.reactivex.rxjava3.internal.util.QueueDrainHelper.request
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
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
@RequestMapping("/danmu/v2")
@Validated
class CompatibleVideoDanmuController {

    /**
     * 加载弹幕列表
     */
    @PostMapping("/loadDanmu")
    fun danmukuLoad(@RequestBody @Validated request: DanmukuLoad.Request): List<DanmukuLoad.DanmukuItem> {
        // 调用查询获取弹幕列表
        val queryResult = Mediator.Companion.queries.send(
            GetDanmukuByFileIdQry.Request(
                fileId = request.fileId.toLong(),
                videoId = request.videoId.toLong()
            )
        )

        // 转换为前端需要的格式
        return queryResult.map { danmuku ->
            DanmukuLoad.DanmukuItem(
                danmukuId = danmuku.danmukuId.toString(),
                fileId = danmuku.fileId.toString(),
                videoId = danmuku.videoId.toString(),
                userId = danmuku.userId.toString(),
                text = danmuku.text,
                mode = danmuku.mode,
                color = danmuku.color,
                time = danmuku.time,
                postTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(danmuku.postTime),
                    ZoneId.systemDefault()
                ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            )
        }
    }

    /**
     * 发送弹幕
     */
    @PostMapping("/postDanmu")
    fun danmukuPost(
        videoId: Long,
        fileId: Long,
        @NotEmpty @Size(max = 200) text: String,
        mode: Int,
        @NotEmpty color: String,
        time: Int,
    ): DanmukuPost.Response {
        // 调用命令发送弹幕
        val userId = LoginHelper.getUserId()!!
        Mediator.commands.send(
            PostDanmukuCmd.Request(
                videoId = videoId,
                fileId = fileId,
                customerId = userId,
                text = text,
                mode = mode,
                color = color,
                time = time
            )
        )
        return DanmukuPost.Response()
    }

}
