package edu.only4.danmuku.adapter.portal.api

import edu.only4.danmuku.adapter.portal.api.payload.DanmuLoad
import edu.only4.danmuku.adapter.portal.api.payload.DanmuPost
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
    fun danmuLoad(@RequestBody @Validated request: DanmuLoad.Request): DanmuLoad.Response {
        // TODO: 实现加载弹幕列表逻辑
        return DanmuLoad.Response()
    }

    /**
     * 发送弹幕
     */
    @PostMapping("/postDanmu")
    fun danmuPost(@RequestBody @Validated request: DanmuPost.Request): DanmuPost.Response {
        // TODO: 实现发送弹幕逻辑
        return DanmuPost.Response()
    }

}
