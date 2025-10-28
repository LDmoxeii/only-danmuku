package edu.only4.danmuku.adapter.portal.api.compatible

import com.only.engine.redis.misc.RedisUtils
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api._share.constant.Constants
import edu.only4.danmuku.adapter.portal.api.payload.*
import edu.only4.danmuku.application.queries.video.*
import edu.only4.danmuku.application.queries.video_file.GetVideoFilesByVideoIdQry
import edu.only4.danmuku.domain.aggregates.customer_profile.CustomerProfile_.avatar
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
 * 视频浏览控制器 - 处理视频列表加载、搜索、详情查看等操作
 */
@RestController
@RequestMapping("/video")
@Validated
class CompatibleVideoController {

    companion object {
        // 简易在线设备心跳表：fileId -> (deviceId -> lastSeenEpochSeconds)
        private val ONLINE_HEARTBEAT: MutableMap<String, MutableMap<String, Long>> = mutableMapOf()
        private const val ONLINE_TTL_SECONDS: Long = 60 // 超过该秒数未上报则视为离线
    }

    @PostMapping("/loadRecommendVideo")
    fun videoLoadRecommend(): Collection<VideoLoadRecommend.VideoItem> {
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

    @PostMapping("/loadVideo")
    fun videoLoad(@RequestBody request: VideoLoad.Request): PageData<VideoLoad.VideoItem> {
        val recommendType = if (request.categoryId == null && request.pCategoryId == null) 0 else null

        val queryRequest = GetVideosByCategoryQry.Request(
            parentCategoryId = request.pCategoryId,
            categoryId = request.categoryId,
            recommendType = recommendType
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum =  queryRequest.pageNum,
            pageSize =  queryRequest.pageSize,

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
    fun videoLoadPList(@RequestBody @Validated request: VideoLoadPList.Request): VideoLoadPList.Response {
        // 调用查询获取视频文件列表
        val fileList = Mediator.queries.send(
            GetVideoFilesByVideoIdQry.Request(videoId = request.videoId.toLong())
        )

        return VideoLoadPList.Response(
            list = fileList.map { file ->
                VideoLoadPList.FileItem(
                    fileId = file.fileId.toString(),
                    videoId = file.videoId.toString(),
                    fileIndex = file.fileIndex,
                    fileName = file.fileName,
                    fileSize = file.fileSize,
                    filePath = file.filePath,
                    duration = file.duration
                )
            }
        )
    }

    @PostMapping("/getVideoInfo")
    fun videoGetInfo(@RequestBody @Validated request: VideoGetInfo.Request): VideoGetInfo.Response {
        // TODO: 从上下文获取当前用户ID
        val currentUserId: Long? = null // 临时设为null表示未登录

        // 调用查询获取视频详情
        val videoInfo = Mediator.queries.send(
            GetVideoInfoQry.Request(videoId = request.videoId.toLong())
        )

        // 如果用户已登录，查询用户对该视频的行为
        val userActionList = mutableListOf<VideoGetInfo.UserAction>()
        if (currentUserId != null) {
            // TODO: 实现查询用户行为逻辑 (点赞、收藏、投币)
            // 需要创建 GetUserActionsByVideoIdQry 查询
        }

        return VideoGetInfo.Response(
            videoInfo = VideoGetInfo.VideoInfo(
                videoId = videoInfo.videoId.toString(),
                videoCover = videoInfo.videoCover,
                videoName = videoInfo.videoName,
                userId = videoInfo.userId.toString(),
                nickName = videoInfo.nickName,
                avatar = videoInfo.avatar,
                introduction = videoInfo.introduction,
                interaction = videoInfo.interaction,
                duration = videoInfo.duration,
                playCount = videoInfo.playCount,
                likeCount = videoInfo.likeCount,
                danmuCount = videoInfo.danmuCount,
                commentCount = videoInfo.commentCount,
                coinCount = videoInfo.coinCount,
                collectCount = videoInfo.collectCount,
                createTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(videoInfo.createTime),
                    ZoneId.systemDefault()
                ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ),
            userActionList = userActionList
        )
    }

    /**
     * 获取推荐视频(基于关键词)
     * @param keyword 关键词
     * @param videoId 当前视频ID(排除)
     * @return 响应结果
     */
    @PostMapping("/getVideoRecommend")
    fun videoGetRecommend(@RequestBody @Validated request: VideoGetRecommend.Request): VideoGetRecommend.Response {
        // 调用搜索查询获取相关视频(最多10个)
        val queryRequest = SearchVideosQry.Request(
            videoNameFuzzy = request.keyword,
            recommendType = null
        ).apply {
            pageNum = 1
            pageSize = 10
        }

        val queryResult = Mediator.queries.send(queryRequest)

        // 过滤掉当前视频
        val filteredList = queryResult.list.filter { it.videoId.toString() != request.videoId }

        return VideoGetRecommend.Response(
            list = filteredList.map { video ->
                VideoGetRecommend.VideoItem(
                    videoId = video.videoId.toString(),
                    videoCover = video.videoCover,
                    videoName = video.videoName,
                    userId = video.userId.toString(),
                    nickName = video.nickName,
                    avatar = video.avatar,
                    playCount = video.playCount,
                    likeCount = video.likeCount
                )
            }
        )
    }

    /**
     * 上报在线播放数
     * @param fileId 文件ID
     * @param deviceId 设备ID
     * @return 响应结果
     */
    @PostMapping("/reportVideoPlayOnline")
    fun videoReportPlayOnline(@RequestBody @Validated request: VideoReportPlayOnline.Request): VideoReportPlayOnline.Response {

        // TODO： 优化使用Redis
        val fileId = request.fileId
        val deviceId = request.deviceId ?: "unknown"

        val now = System.currentTimeMillis() / 1000

        // 获取/创建该文件的设备心跳表
        val deviceMap = ONLINE_HEARTBEAT.getOrPut(fileId) { mutableMapOf() }

        // 更新当前设备心跳时间
        deviceMap[deviceId] = now

        // 清理过期设备
        val expiry = now - ONLINE_TTL_SECONDS
        val iterator = deviceMap.entries.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (entry.value < expiry) {
                iterator.remove()
            }
        }

        // 返回当前在线数量
        return VideoReportPlayOnline.Response(count = deviceMap.size)
    }

    /**
     * 搜索视频
     * @param keyword 搜索关键词
     * @param orderType 排序类型
     * @param pageNo 页码
     * @return 响应结果
     */
    @PostMapping("/search")
    fun videoSearch(@RequestBody @Validated request: VideoSearch.Request): VideoSearch.Response {
        // TODO: 实现记录搜索关键词到Redis用于热词统计
        // redisComponent.addKeywordCount(request.keyword)

        // 调用搜索查询
        val queryRequest = SearchVideosQry.Request(
            videoNameFuzzy = request.keyword,
            recommendType = null
        ).apply {
            pageNum = request.pageNo ?: 1
            pageSize = 30
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return VideoSearch.Response(
            list = queryResult.list.map { video ->
                VideoSearch.VideoItem(
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
            },
            pageNo = queryResult.pageNum,
            totalCount = queryResult.totalCount.toInt()
        )
    }

    /**
     * 获取热门搜索关键词
     * @return 响应结果
     */
    @PostMapping("/getSearchKeywordTop")
    fun videoGetSearchKeywordTop(): Collection<String> =
        RedisUtils.getCacheZSetRange(Constants.REDIS_KEY_VIDEO_SEARCH_COUNT, 0, 9)


    /**
     * 加载热门视频列表(24小时内)
     * @param pageNo 页码
     * @return 响应结果
     */
    @PostMapping("/loadHotVideoList")
    fun videoLoadHot(@RequestBody request: VideoLoadHot.Request): VideoLoadHot.Response {
        // 调用查询获取热门视频列表
        val queryRequest = GetHotVideosQry.Request(lastPlayHour = 24).apply {
            pageNum = request.pageNo ?: 1
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return VideoLoadHot.Response(
            list = queryResult.list.map { video ->
                VideoLoadHot.VideoItem(
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
            },
            pageNo = queryResult.pageNum,
            totalCount = queryResult.totalCount.toInt()
        )
    }

}
