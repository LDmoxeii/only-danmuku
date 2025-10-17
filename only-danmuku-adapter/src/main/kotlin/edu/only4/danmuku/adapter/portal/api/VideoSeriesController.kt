package edu.only4.danmuku.adapter.portal.api

import edu.only4.danmuku.adapter.portal.api.payload.*
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 视频系列控制器 - 处理视频系列管理操作
 */
@RestController
@RequestMapping("/uhome/series")
@Validated
class VideoSeriesController {

    /**
     * 加载视频系列列表
     */
    @PostMapping("/loadVideoSeries")
    fun videoSeriesLoad(@RequestBody @Validated request: VideoSeriesLoad.Request): VideoSeriesLoad.Response {
        // TODO: 实现加载视频系列列表逻辑
        return VideoSeriesLoad.Response()
    }

    /**
     * 保存视频系列
     */
    @PostMapping("/saveVideoSeries")
    fun videoSeriesSave(@RequestBody @Validated request: VideoSeriesSave.Request): VideoSeriesSave.Response {
        // TODO: 实现保存视频系列逻辑
        return VideoSeriesSave.Response()
    }

    /**
     * 加载所有视频(用于添加到系列)
     */
    @PostMapping("/loadAllVideo")
    fun videoSeriesLoadAllVideo(@RequestBody request: VideoSeriesLoadAllVideo.Request): VideoSeriesLoadAllVideo.Response {
        // TODO: 实现加载所有视频(用于添加到系列)逻辑
        return VideoSeriesLoadAllVideo.Response()
    }

    /**
     * 获取系列详情
     */
    @PostMapping("/getVideoSeriesDetail")
    fun videoSeriesGetDetail(@RequestBody @Validated request: VideoSeriesGetDetail.Request): VideoSeriesGetDetail.Response {
        // TODO: 实现获取系列详情逻辑
        return VideoSeriesGetDetail.Response()
    }

    /**
     * 保存系列视频
     */
    @PostMapping("/saveSeriesVideo")
    fun videoSeriesSaveVideo(@RequestBody @Validated request: VideoSeriesSaveVideo.Request): VideoSeriesSaveVideo.Response {
        // TODO: 实现保存系列视频逻辑
        return VideoSeriesSaveVideo.Response()
    }

    /**
     * 删除系列中的视频
     */
    @PostMapping("/delSeriesVideo")
    fun videoSeriesDelVideo(@RequestBody @Validated request: VideoSeriesDelVideo.Request): VideoSeriesDelVideo.Response {
        // TODO: 实现删除系列中的视频逻辑
        return VideoSeriesDelVideo.Response()
    }

    /**
     * 删除视频系列
     */
    @PostMapping("/delVideoSeries")
    fun videoSeriesDel(@RequestBody @Validated request: VideoSeriesDel.Request): VideoSeriesDel.Response {
        // TODO: 实现删除视频系列逻辑
        return VideoSeriesDel.Response()
    }

    /**
     * 调整系列排序
     */
    @PostMapping("/changeVideoSeriesSort")
    fun videoSeriesChangeSort(@RequestBody @Validated request: VideoSeriesChangeSort.Request): VideoSeriesChangeSort.Response {
        // TODO: 实现调整系列排序逻辑
        return VideoSeriesChangeSort.Response()
    }

    /**
     * 加载系列及关联视频
     */
    @PostMapping("/loadVideoSeriesWithVideo")
    fun videoSeriesLoadWithVideo(@RequestBody @Validated request: VideoSeriesLoadWithVideo.Request): VideoSeriesLoadWithVideo.Response {
        // TODO: 实现加载系列及关联视频逻辑
        return VideoSeriesLoadWithVideo.Response()
    }

}
