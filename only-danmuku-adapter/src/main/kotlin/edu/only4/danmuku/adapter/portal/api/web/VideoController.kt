package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.video.ReportVideoPlayOnline
import edu.only4.danmuku.adapter.portal.api.payload.video.GetVideoDetail
import edu.only4.danmuku.adapter.portal.api.payload.video.GetVideoRecommendList
import edu.only4.danmuku.adapter.portal.api.payload.video.GetVideoPage
import edu.only4.danmuku.adapter.portal.api.payload.video.GetHotVidePage
import edu.only4.danmuku.adapter.portal.api.payload.video.GetVideoPList
import edu.only4.danmuku.adapter.portal.api.payload.video.GetRecommendVideoList
import edu.only4.danmuku.adapter.portal.api.payload.video.VideoSearch
import edu.only4.danmuku.application.distributed.clients.statistics.ReportVideoPlayOnlineCli
import edu.only4.danmuku.application.distributed.clients.statistics.ReportVideoSearchCountCli
import edu.only4.danmuku.application.queries.customer_action.GetUserActionsByVideoIdQry
import edu.only4.danmuku.application.queries.statistics.GetSearchKeywordTopListQry
import edu.only4.danmuku.application.queries.video.GetHotVideoPageQry
import edu.only4.danmuku.application.queries.video.GetRecommendVideosQry
import edu.only4.danmuku.application.queries.video.GetVideoInfoQry
import edu.only4.danmuku.application.queries.video_file.GetVideoFilesByVideoIdQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/video")
class VideoController {

    @SaIgnore
    @PostMapping("/getRecommendVideoList")
    fun getRecommendVideoList(): List<GetRecommendVideoList.Item> {
        val videoList = Mediator.queries.send(GetRecommendVideosQry.Request())
        return videoList.map { GetRecommendVideoList.Converter.INSTANCE.fromApp(it) }
    }

    @SaIgnore
    @PostMapping("/page")
    fun page(@RequestBody @Validated request: GetVideoPage.Request): PageData<GetVideoPage.Item> {

        val queryResult = Mediator.queries.send(GetVideoPage.Converter.INSTANCE.toQry(request))

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { GetVideoPage.Converter.INSTANCE.fromQry(it) },
            totalCount = queryResult.totalCount,
        )
    }

    @SaIgnore
    @PostMapping("/getVideoPList")
    fun getVideoPList(@RequestBody @Validated request: GetVideoPList.Request): List<GetVideoPList.FileItem> {
        val fileList = Mediator.queries.send(
            GetVideoFilesByVideoIdQry.Request(request.videoId)
        )

        return fileList.map { GetVideoPList.Converter.INSTANCE.fromApp(it) }
    }

    @SaIgnore
    @PostMapping("/detail")
    fun detail(@RequestBody @Validated request: GetVideoDetail.Request): GetVideoDetail.Response {
        val currentUserId = LoginHelper.getUserId()

        // 调用查询获取视频详情
        val videoInfo = Mediator.queries.send(
            GetVideoInfoQry.Request(videoId = request.videoId)
        )

        val userActionList = currentUserId?.let {
            val actionList = Mediator.queries.send(
                GetUserActionsByVideoIdQry.Request(
                    userId = currentUserId,
                    videoId = request.videoId
                )
            )
            actionList.map { GetVideoDetail.Converter.INSTANCE.fromApp(it) }
        } ?: emptyList()

        return GetVideoDetail.Response(
            videoInfo = GetVideoDetail.Converter.INSTANCE.fromApp(videoInfo),
            userActionList = userActionList
        )
    }

    @SaIgnore
    @PostMapping("/getVideoRecommendList")
    fun getVideoRecommendList(@RequestBody @Validated  request: GetVideoRecommendList.Request): List<GetVideoRecommendList.Item> {
        val queryResult = Mediator.queries.send(GetVideoRecommendList.Converter.INSTANCE.toQry(request))

        return queryResult.list.map { GetVideoRecommendList.Converter.INSTANCE.fromQry(it) }
    }

    @SaIgnore
    @PostMapping("/reportVideoPlayOnline")
    fun reportVideoPlayOnline(@RequestBody @Validated request: ReportVideoPlayOnline.Request): Long = Mediator.requests.send(
        ReportVideoPlayOnlineCli.Request(
            fileId = request.fileId,
            deviceId = request.deviceId
        )
    ).current

    @PostMapping("/search")
    fun search(@RequestBody @Validated request: VideoSearch.Request): PageData<VideoSearch.Item> {
        val queryResult = Mediator.queries.send(
            VideoSearch.Converter.INSTANCE.toQry(request)
        )

        Mediator.requests.send(
            ReportVideoSearchCountCli.Request(
                keyword = request.keyword
            )
        )

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { VideoSearch.Converter.INSTANCE.fromQry(it) },
            totalCount = queryResult.totalCount,
        )
    }

    @SaIgnore
    @PostMapping("/getSearchKeywordTop")
    fun getSearchKeywordTop(): Collection<String> {
        return Mediator.queries.send(
            GetSearchKeywordTopListQry.Request()
        ).map { it.keyword }
    }

    @PostMapping("/getHotVidePage")
    fun getHotVidePage(@RequestBody @Validated request: GetHotVidePage.Request): PageData<GetHotVidePage.Item> {
        val queryResult = Mediator.queries.send(GetHotVidePage.Converter.INSTANCE.toQry(request))

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,

            list = queryResult.list.map { GetHotVidePage.Converter.INSTANCE.fromQry(it) },

            totalCount = queryResult.totalCount,
        )
    }

}
