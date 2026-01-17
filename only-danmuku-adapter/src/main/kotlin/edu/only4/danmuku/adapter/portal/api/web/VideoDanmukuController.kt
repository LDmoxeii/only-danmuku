package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.adapter.portal.api.payload.video_danmuku.GetDanmukuList
import edu.only4.danmuku.adapter.portal.api.payload.video_danmuku.PostDanmuku
import edu.only4.danmuku.application.commands.video_danmuku.PostDanmukuCmd
import edu.only4.danmuku.application.queries.video_danmuku.GetDanmukuListByFileIdQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/danmuku")
class VideoDanmukuController {

    /**
     * 加载弹幕列表
     */
    @SaIgnore
    @PostMapping("/getList")
    fun getList(@RequestBody @Validated request: GetDanmukuList.Request): List<GetDanmukuList.Item> {
        // 调用查询获取弹幕列表
        val queryResult = Mediator.queries.send(
            GetDanmukuListByFileIdQry.Request(
                fileId = request.fileId,
                videoId = request.videoId
            )
        )

        // 转换为前端需要的格式
        val list = queryResult.map { danmuku ->
            GetDanmukuList.Item(
                danmukuId = danmuku.danmukuId.toString(),
                fileId = danmuku.fileId.toString(),
                videoId = danmuku.videoId.toString(),
                userId = danmuku.userId.toString(),
                text = danmuku.text,
                mode = danmuku.mode,
                color = danmuku.color,
                time = danmuku.time,
                postTime = danmuku.postTime,
                videoName = danmuku.videoName,
                videoCover = danmuku.videoCover,
                nickName = danmuku.nickName
            )
        }
        return list
    }

    @PostMapping("/postDanmuku")
    fun postDanmuku(@RequestBody @Validated request: PostDanmuku.Request) = Mediator.commands.send(
        PostDanmukuCmd.Request(
            videoId = request.videoId,
            fileId = request.fileId,
            customerId = LoginHelper.getUserId()!!,
            text = request.text,
            mode = request.mode,
            color = request.color,
            time = request.time
        )
    )

}
