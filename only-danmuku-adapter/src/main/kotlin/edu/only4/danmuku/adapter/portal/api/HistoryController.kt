package edu.only4.danmuku.adapter.portal.api

import edu.only4.danmuku.adapter.portal.api.payload.HistoryClean
import edu.only4.danmuku.adapter.portal.api.payload.HistoryDel
import edu.only4.danmuku.adapter.portal.api.payload.HistoryLoad
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
@Validated
class HistoryController {

    /**
     * 加载播放历史
     */
    @PostMapping("/loadHistory")
    fun historyLoad(@RequestBody request: HistoryLoad.Request): HistoryLoad.Response {
        // TODO: 实现加载播放历史逻辑
        return HistoryLoad.Response()
    }

    /**
     * 清空播放历史
     */
    @PostMapping("/cleanHistory")
    fun historyClean(): HistoryClean.Response {
        // TODO: 实现清空播放历史逻辑
        return HistoryClean.Response()
    }

    /**
     * 删除指定播放历史
     */
    @PostMapping("/delHistory")
    fun historyDel(@RequestBody @Validated request: HistoryDel.Request): HistoryDel.Response {
        // TODO: 实现删除指定播放历史逻辑
        return HistoryDel.Response()
    }

}
