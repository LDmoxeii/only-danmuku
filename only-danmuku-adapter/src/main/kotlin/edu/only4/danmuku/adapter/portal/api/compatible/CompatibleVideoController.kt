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
import jakarta.validation.constraints.NotEmpty
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/video")
@Validated
class CompatibleVideoController {

    @SaIgnore
    @PostMapping("/loadRecommendVideo")
    fun getRecommendVideos(): Collection<VideoLoadRecommend.VideoItem> {
        val videoList = Mediator.queries.send(GetRecommendVideosQry.Request())

        return videoList.map { video ->
            VideoLoadRecommend.VideoItem(
                videoId = video.videoId.toString(),
                videoCover = video.videoCover,
                videoName = video.videoName,
                userId = video.userId.toString(),
                nickName = video.nickName,
                avatar = video.avatar,
                playCount = video.playCount,
                likeCount = video.likeCount,
                createTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(video.createTime),
                    ZoneId.systemDefault()
                ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            )
        }
    }

    @SaIgnore
    @PostMapping("/loadVideo")
    fun getVideoPage(request: VideoLoad.Request): PageData<VideoLoad.VideoItem> {
        val recommendType = if (request.categoryId == null && request.categoryParentId == null) 0 else null

        val queryRequest = GetVideoPageQry.Request(
            categoryParentId = request.categoryParentId,
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

            list = queryResult.list.map { video ->
                VideoLoad.VideoItem(
                    videoId = video.videoId,
                    videoCover = video.videoCover,
                    videoName = video.videoName,
                    userId = video.userId,
                    createTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(video.createTime),
                        ZoneId.systemDefault()
                    ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    lastUpdateTime = video.lastUpdateTime?.let {
                        LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(it),
                            ZoneId.systemDefault()
                        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    },
                    parentCategoryId = video.parentCategoryId,
                    categoryId = video.categoryId,
                    postType = video.postType,
                    originInfo = video.originInfo,
                    tags = video.tags,
                    introduction = video.introduction,
                    duration = video.duration,
                    playCount = video.playCount,
                    likeCount = video.likeCount,
                    danmuCount = video.danmuCount,
                    commentCount = video.commentCount,
                    coinCount = video.coinCount,
                    collectCount = video.collectCount,
                    recommendType = video.recommendType,
                    lastPlayTime = video.lastPlayTime?.let {
                        LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(it),
                            ZoneId.systemDefault()
                        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    },
                    nickName = video.nickName,
                    avatar = video.avatar,
                    categoryFullName = video.categoryFullName,
                )
            },

            totalCount = queryResult.totalCount,
        )
    }

    @PostMapping("/loadVideoPList")
    fun getVideoPList(
        videoId: Long,
    ): List<VideoLoadPList.FileItem> {
        val fileList = Mediator.queries.send(
            GetVideoFilesByVideoIdQry.Request(videoId)
        )

        return fileList.map { file ->
            VideoLoadPList.FileItem(
                fileId = file.fileId,
                videoId = file.videoId,
                userId = file.userId,
                fileIndex = file.fileIndex,
                fileName = file.fileName,
                fileSize = file.fileSize,
                filePath = file.filePath,
                duration = file.duration
            )
        }
    }

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
            actionList.map { act ->
                VideoGetInfo.UserAction(
                    actionId = act.actionId,
                    userId = act.userId,
                    videoId = act.videoId,
                    videoName = act.videoName,
                    videoCover = act.videoCover,
                    videoUserId = act.videoUserId,
                    commentId = act.commentId,
                    actionType = act.actionType,
                    actionCount = act.actionCount,
                    cationTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(act.actionTime),
                        ZoneId.systemDefault()
                    ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
            }
        } ?: emptyList()

        return VideoGetInfo.Response(
            videoInfo = VideoGetInfo.VideoInfo(
                videoId = videoInfo.videoId,
                videoCover = videoInfo.videoCover,
                videoName = videoInfo.videoName,
                userId = videoInfo.userId,
                createTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(videoInfo.createTime),
                    ZoneId.systemDefault()
                ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                postType = 0,
                originInfo = videoInfo.originInfo,
                tags = videoInfo.tags,
                introduction = videoInfo.introduction,
                interaction = videoInfo.interaction,
                playCount = videoInfo.playCount,
                likeCount = videoInfo.likeCount,
                danmuCount = videoInfo.danmuCount,
                commentCount = videoInfo.commentCount,
                coinCount = videoInfo.coinCount,
                collectCount = videoInfo.collectCount
            ),
            userActionList = userActionList
        )
    }

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

        return queryResult.list.map { video ->
            VideoGetRecommend.VideoItem(
                videoId = video.videoId,
                videoCover = video.videoCover,
                videoName = video.videoName,
                userId = video.userId!!,
                nickName = video.nickName,
                avatar = video.avatar,
                playCount = video.playCount,
                likeCount = video.likeCount,
                createTime = video.createTime,
                lastUpdateTime = video.lastUpdateTime,
                parentCategoryId = video.parentCategoryId,
                categoryId = video.categoryId,
                postType = video.postType,
                originInfo = video.originInfo,
                tags = video.tags,
                introduction = video.introduction,
                duration = video.duration,
                status = video.status,
                danmuCount = video.danmuCount,
                commentCount = video.commentCount,
                coinCount = video.coinCount,
                collectCount = video.collectCount,
                recommendType = video.recommendType,
                lastPlayTime = video.lastPlayTime,
                categoryFullName = video.categoryFullName
            )
        }
    }

    @PostMapping("/reportVideoPlayOnline")
    fun reportVideoPlayOnline(
        fileId: Long,
        deviceId: Long,
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

            list = queryResult.list.map { video ->
                VideoSearch.VideoItem(
                    videoId = video.videoId,
                    videoCover = video.videoCover,
                    videoName = video.videoName,
                    userId = video.userId,
                    createTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(video.createTime),
                        ZoneId.systemDefault()
                    ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    lastUpdateTime = video.lastUpdateTime?.let {
                        LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(it),
                            ZoneId.systemDefault()
                        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    },
                    parentCategoryId = video.parentCategoryId,
                    categoryId = video.categoryId,
                    postType = video.postType,
                    originInfo = video.originInfo,
                    tags = video.tags,
                    introduction = video.introduction,
                    duration = video.duration,
                    playCount = video.playCount,
                    likeCount = video.likeCount,
                    danmuCount = video.danmuCount,
                    commentCount = video.commentCount,
                    coinCount = video.coinCount,
                    collectCount = video.collectCount,
                    recommendType = video.recommendType,
                    lastPlayTime = video.lastPlayTime?.let {
                        LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(it),
                            ZoneId.systemDefault()
                        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    },
                    nickName = video.nickName,
                    avatar = video.avatar,
                    categoryFullName = video.categoryFullName,
                )
            },

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

            list = queryResult.list.map { video ->
                VideoLoadHot.VideoItem(
                    videoId = video.videoId,
                    videoCover = video.videoCover,
                    videoName = video.videoName,
                    userId = video.userId,
                    createTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(video.createTime),
                        ZoneId.systemDefault()
                    ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    lastUpdateTime = video.lastUpdateTime?.let {
                        LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(it),
                            ZoneId.systemDefault()
                        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    },
                    parentCategoryId = video.parentCategoryId,
                    categoryId = video.categoryId,
                    postType = video.postType,
                    originInfo = video.originInfo,
                    tags = video.tags,
                    introduction = video.introduction,
                    duration = video.duration,
                    playCount = video.playCount,
                    likeCount = video.likeCount,
                    danmuCount = video.danmuCount,
                    commentCount = video.commentCount,
                    coinCount = video.coinCount,
                    collectCount = video.collectCount,
                    recommendType = video.recommendType,
                    lastPlayTime = video.lastPlayTime?.let {
                        LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(it),
                            ZoneId.systemDefault()
                        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    },
                    nickName = video.nickName,
                    avatar = video.avatar,
                    categoryFullName = video.categoryFullName,
                )
            },

            totalCount = queryResult.totalCount,
        )
    }

}
