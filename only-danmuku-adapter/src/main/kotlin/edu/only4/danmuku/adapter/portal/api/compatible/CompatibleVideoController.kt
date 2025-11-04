package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.redis.misc.RedisUtils
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api._share.constant.Constants
import edu.only4.danmuku.adapter.portal.api.payload.*
import edu.only4.danmuku.application.queries.customer_action.GetUserActionsByVideoIdQry
import edu.only4.danmuku.application.queries.video.GetHotVideoPageQry
import edu.only4.danmuku.application.queries.video.GetRecommendVideosQry
import edu.only4.danmuku.application.queries.video.GetVideoInfoQry
import edu.only4.danmuku.application.queries.video.GetVideoPageQry
import edu.only4.danmuku.application.queries.video_file.GetVideoFilesByVideoIdQry
import edu.only4.danmuku.domain.aggregates.video.enums.RecommendType
import jakarta.validation.constraints.NotEmpty
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
@RequestMapping("/video")
@Validated
class CompatibleVideoController {

    @SaIgnore
    @PostMapping("/loadRecommendVideo")
    fun getRecommendVideos(): Collection<VideoLoadRecommend.VideoItem> {
        val videoList = Mediator.queries.send(GetRecommendVideosQry.Request())
        return videoList.map { VideoLoadRecommend.Converter.INSTANCE.fromApp(it) }
    }

    @SaIgnore
    @PostMapping("/loadVideo")
    fun getVideoPage(request: VideoLoad.Request): PageData<VideoLoad.VideoItem> {
        val recommendType = if (request.categoryId == null && request.parentCategoryId == null) RecommendType.NOT_RECOMMEND else null

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
            list = queryResult.list.map { VideoLoad.Converter.INSTANCE.fromApp(it) },
            totalCount = queryResult.totalCount,
        )
    }

    @SaIgnore
    @PostMapping("/loadVideoPList")
    fun getVideoPList(
        videoId: Long,
    ): List<VideoLoadPList.FileItem> {
        val fileList = Mediator.queries.send(
            GetVideoFilesByVideoIdQry.Request(videoId)
        )

        return fileList.map { VideoLoadPList.Converter.INSTANCE.fromApp(it) }
    }

    @SaIgnore
    @PostMapping("/getVideoInfo")
    fun getVideoInfo(
        videoId: Long
    ): VideoGetInfo.Response {
        val currentUserId = LoginHelper.getUserId()

        // 调用查询获取视频详情
        val videoInfo = Mediator.queries.send(
            GetVideoInfoQry.Request(videoId = videoId)
        )

        val userActionList = currentUserId?.let {
            val actionList = Mediator.queries.send(
                GetUserActionsByVideoIdQry.Request(
                    userId = currentUserId,
                    videoId = videoId
                )
            )
            actionList.map { VideoGetInfo.Converter.INSTANCE.fromApp(it) }
        } ?: emptyList()

        return VideoGetInfo.Response(
            videoInfo = VideoGetInfo.Converter.INSTANCE.fromApp(videoInfo),
            userActionList = userActionList
        )
    }

    @SaIgnore
    @PostMapping("/getVideoRecommend")
    fun getRecommendVideo(
        @NotEmpty keyword: String,
        videoId: Long,
    ): List<VideoGetRecommend.VideoItem> {
        val queryRequest = GetVideoPageQry.Request(
            videoNameFuzzy = keyword,
            recommendType = null,
            excludeVideoIds = listOf(videoId)
        ).apply {
            pageNum = 1
            pageSize = 10
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return queryResult.list.map { VideoGetRecommend.Converter.INSTANCE.fromApp(it) }
    }

    @SaIgnore
    @PostMapping("/reportVideoPlayOnline")
    fun reportVideoPlayOnline(
        fileId: Long,
        deviceId: String,
    ): Long {
        val userPlayOnlineKey = String.format(
            Constants.REDIS_KEY_VIDEO_PLAY_COUNT_USER,
            fileId,
            deviceId
        )
        val playOnlineCountKey = String.format(
            Constants.REDIS_KEY_VIDEO_PLAY_COUNT_ONLINE,
            fileId
        )

        return if (!RedisUtils.hasKey(userPlayOnlineKey)) {
            // 首次该设备上报：记录设备在线心跳并计数 +1，设置计数 TTL
            RedisUtils.setCacheObject(
                userPlayOnlineKey,
                fileId,
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
    fun searchVideo(@Validated request: VideoSearch.Request): PageData<VideoSearch.VideoItem> {
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

    @PostMapping("/loadHotVideoList")
    fun getHotVidePage(request: VideoLoadHot.Request): PageData<VideoLoadHot.VideoItem> {
        val queryRequest = GetHotVideoPageQry.Request(lastPlayHour = 24).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryRequest.pageNum,
            pageSize = queryRequest.pageSize,

            list = queryResult.list.map { VideoLoadHot.Converter.INSTANCE.fromApp(it) },

            totalCount = queryResult.totalCount,
        )
    }

}
