package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.DanmukuLoad
import edu.only4.danmuku.application.commands.video_danmuku.PostDanmukuCmd
import edu.only4.danmuku.application.queries.video_danmuku.GetDanmukuListByFileIdQry
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/danmu")
@Validated
class CompatibleVideoDanmuController {

    /**
     * 加载弹幕列表
     */
    @SaIgnore
    @PostMapping("/loadDanmu")
    fun getVideoDanmukuList(
        fileId: Long,
        videoId: Long,
    ): List<DanmukuLoad.DanmukuItem> {
        // 调用查询获取弹幕列表
        val queryResult = Mediator.queries.send(
            GetDanmukuListByFileIdQry.Request(
                fileId = fileId,
                videoId = videoId
            )
        )

        // 转换为前端需要的格式
        val list = queryResult.map { danmuku ->
            DanmukuLoad.DanmukuItem(
                danmukuId = danmuku.danmukuId.toString(),
                fileId = danmuku.fileId.toString(),
                videoId = danmuku.videoId.toString(),
                userId = danmuku.userId.toString(),
                text = danmuku.text,
                mode = danmuku.mode,
                color = danmuku.color,
                time = danmuku.time,
                // 直接返回秒级时间戳，交给翻译器序列化
                postTime = danmuku.postTime,
                videoName = danmuku.videoName,
                videoCover = danmuku.videoCover,
                nickName = danmuku.nickName
            )
        }
        return list
    }

    @PostMapping("/postDanmu")
    fun postDanmuku(
        videoId: Long,
        fileId: Long,
        @NotEmpty @Size(max = 200) text: String,
        mode: Int,
        @NotEmpty color: String,
        time: Int,
    ) {
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
    }

}
