package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.redis.misc.RedisUtils
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api._share.constant.Constants
import edu.only4.danmuku.adapter.portal.api.payload.video.ReportVideoPlayOnline
import edu.only4.danmuku.adapter.portal.api.payload.video.GetVideoDetail
import edu.only4.danmuku.adapter.portal.api.payload.video.GetVideoRecommendList
import edu.only4.danmuku.adapter.portal.api.payload.video.GetVideoPage
import edu.only4.danmuku.adapter.portal.api.payload.video.GetHotVidePage
import edu.only4.danmuku.adapter.portal.api.payload.video.GetVideoPList
import edu.only4.danmuku.adapter.portal.api.payload.video.GetRecommendVideoList
import edu.only4.danmuku.adapter.portal.api.payload.video.VideoSearch
import edu.only4.danmuku.application.queries.customer_action.GetUserActionsByVideoIdQry
import edu.only4.danmuku.application.queries.video.GetHotVideoPageQry
import edu.only4.danmuku.application.queries.video.GetRecommendVideosQry
import edu.only4.danmuku.application.queries.video.GetVideoInfoQry
import edu.only4.danmuku.application.queries.video.GetVideoPageQry
import edu.only4.danmuku.application.queries.video_file.GetVideoFilesByVideoIdQry
import edu.only4.danmuku.domain.aggregates.video.enums.RecommendType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

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
        val recommendType =
            if (request.categoryId == null && request.parentCategoryId == null) RecommendType.NOT_RECOMMEND else null

        val queryRequest = GetVideoPageQry.Request(
            categoryParentId = request.parentCategoryId,
            categoryId = request.categoryId,
            recommendType = recommendType
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryRequest.pageNum,
            pageSize = queryRequest.pageSize,
            list = queryResult.list.map { GetVideoPage.Converter.INSTANCE.fromApp(it) },
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
        val queryRequest = GetVideoPageQry.Request(
            videoNameFuzzy = request.keyword,
            recommendType = null,
            excludeVideoIds = listOf(request.videoId)
        ).apply {
            pageNum = 1
            pageSize = 10
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return queryResult.list.map { GetVideoRecommendList.Converter.INSTANCE.fromApp(it) }
    }

    @SaIgnore
    @PostMapping("/reportVideoPlayOnline")
    fun reportVideoPlayOnline(@RequestBody @Validated request: ReportVideoPlayOnline.Request): Long {
        val userPlayOnlineKey = String.format(
            Constants.REDIS_KEY_VIDEO_PLAY_COUNT_USER,
            request.fileId,
            request.deviceId
        )
        val playOnlineCountKey = String.format(
            Constants.REDIS_KEY_VIDEO_PLAY_COUNT_ONLINE,
            request.fileId
        )

        return if (!RedisUtils.hasKey(userPlayOnlineKey)) {
            // 首次该设备上报：记录设备在线心跳并计数 +1，设置计数 TTL
            RedisUtils.setCacheObject(
                userPlayOnlineKey,
                request.fileId,
                Duration.ofSeconds(Constants.VIDEO_PLAY_ONLINE_DEVICE_TTL_SEC)
            )

            val current = if (RedisUtils.hasKey(playOnlineCountKey)) {
                RedisUtils.incrAtomicValue(playOnlineCountKey)
            } else {
                RedisUtils.setAtomicValue(playOnlineCountKey, 1)
                1
            }
            RedisUtils.expire(playOnlineCountKey, Constants.VIDEO_PLAY_ONLINE_COUNT_TTL_SEC)
            current
        } else {
            // 已存在心跳：仅续期心跳与计数 TTL
            RedisUtils.expire(playOnlineCountKey, Constants.VIDEO_PLAY_ONLINE_COUNT_TTL_SEC)
            RedisUtils.expire(userPlayOnlineKey, Constants.VIDEO_PLAY_ONLINE_DEVICE_TTL_SEC)

            val current = RedisUtils.getAtomicValue(playOnlineCountKey)
            if (current <= 0) 1 else current
        }
    }

    @PostMapping("/search")
    fun search(@RequestBody @Validated request: VideoSearch.Request): PageData<VideoSearch.Item> {
        RedisUtils.incrZSetScore(Constants.REDIS_KEY_VIDEO_SEARCH_COUNT, request.keyword, 1.0)

        val queryRequest = GetVideoPageQry.Request(
            videoNameFuzzy = request.keyword,
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryRequest.pageNum,
            pageSize = queryRequest.pageSize,

            list = queryResult.list.map { VideoSearch.Converter.INSTANCE.fromApp(it) },

            totalCount = queryResult.totalCount,
        )
    }

    @SaIgnore
    @PostMapping("/getSearchKeywordTop")
    fun getSearchKeywordTop(): Collection<String> =
        RedisUtils.getCacheZSetRange(Constants.REDIS_KEY_VIDEO_SEARCH_COUNT, 0, 9)

    @PostMapping("/getHotVidePage")
    fun getHotVidePage(@RequestBody @Validated request: GetHotVidePage.Request): PageData<GetHotVidePage.Item> {
        val queryRequest = GetHotVideoPageQry.Request(lastPlayHour = 24).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryRequest.pageNum,
            pageSize = queryRequest.pageSize,

            list = queryResult.list.map { GetHotVidePage.Converter.INSTANCE.fromApp(it) },

            totalCount = queryResult.totalCount,
        )
    }

}
