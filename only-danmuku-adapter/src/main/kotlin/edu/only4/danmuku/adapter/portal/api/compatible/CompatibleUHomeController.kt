package edu.only4.danmuku.adapter.portal.api.compatible

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.*
import edu.only4.danmuku.application.commands.customer_focus.FocusCmd
import edu.only4.danmuku.application.commands.customer_focus.UnFocusCmd
import edu.only4.danmuku.application.commands.customer_profile.UpdateCustomerProfileCmd
import edu.only4.danmuku.application.queries.customer_action.CustomerCollectedVideoIdsQry
import edu.only4.danmuku.application.queries.customer_focus.CheckFocusStatusQry
import edu.only4.danmuku.application.queries.customer_focus.GetFansListQry
import edu.only4.danmuku.application.queries.customer_focus.GetFocusListQry
import edu.only4.danmuku.application.queries.customer_profile.GetCustomerProfileQry
import edu.only4.danmuku.application.queries.video.SearchVideosQry
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/uhome")
@Validated
class CompatibleUHomeController {

    /**
     * 获取用户信息
     */
    @PostMapping("/getUserInfo")
    fun uHomeGetUserInfo(userId :Long): UHomeGetUserInfo.Response {
        val currentUserId = LoginHelper.getUserId()!!

        // 调用查询获取用户详细信息
        val queryResult = Mediator.queries.send(
            GetCustomerProfileQry.Request(
                customerId = userId
            )
        )

        // 检查当前用户是否关注了该用户
        val haveFocus = if (currentUserId != userId) {
            Mediator.queries.send(
                CheckFocusStatusQry.Request(
                    userId = currentUserId,
                    focusUserId = userId
                )
            ).haveFocus
        } else {
            false
        }

        return UHomeGetUserInfo.Response(
            userInfo = UHomeGetUserInfo.UserInfo(
                userId = queryResult.customerId.toString(),
                nickName = queryResult.nickName,
                avatar = queryResult.avatar,
                sex = queryResult.sex,
                birthday = queryResult.birthday,
                school = queryResult.school,
                personIntroduction = queryResult.personIntroduction,
                noticeInfo = queryResult.noticeInfo,
                theme = queryResult.theme,
                currentCoinCount = queryResult.currentCoinCount,
                fansCount = queryResult.fansCount,
                focusCount = queryResult.focusCount,
                haveFocus = haveFocus
            )
        )
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/updateUserInfo")
    fun uHomeUpdateUserInfo(
        @NotEmpty @Size(max = 20) nickName: String,
        @NotEmpty @Size(max = 100) avatar: String,
        sex: Int, birthday: String?,
        @Size(max = 150) school: String?,
        @Size(max = 80) personIntroduction: String?,
        @Size(max = 300) noticeInfo: String?,
    ): UHomeUpdateUserInfo.Response {
        val userId = LoginHelper.getUserId()!!
        // TODO
        // 需要判断是否有足够的硬币修改昵称，
        // 如果修改了昵称，需要发出事件，并触发扣减硬币命令
        // 如果修改了昵称，还需要触发修改token的命令
        // 如果修改头像信息，也需要出发修改token的命令
        Mediator.commands.send(
            UpdateCustomerProfileCmd.Request(
                customerId = userId,
                nickName = nickName,
                avatar = avatar,
                sex = sex,
                birthday = birthday,
                school = school,
                personIntroduction = personIntroduction,
                noticeInfo = noticeInfo
            )
        )

        return UHomeUpdateUserInfo.Response()
    }

    /**
     * 保存用户主题
     */
    @PostMapping("/saveTheme")
    fun uHomeSaveTheme(@RequestBody request: UHomeSaveTheme.Request): UHomeSaveTheme.Response {
        val userId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            UpdateCustomerProfileCmd.Request(
                customerId = userId,
                theme = request.theme
            )
        )

        return UHomeSaveTheme.Response()
    }

    /**
     * 关注用户
     */
    @PostMapping("/focus")
    fun uHomeFocus(focusUserId: Long): UHomeFocus.Response {
        val currentUserId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            FocusCmd.Request(
                userId = currentUserId,
                focusUserId = focusUserId
            )
        )

        return UHomeFocus.Response()
    }

    /**
     * 取消关注
     */
    @PostMapping("/cancelFocus")
    fun uHomeCancelFocus(focusUserId: Long): UHomeCancelFocus.Response {
        val currentUserId = LoginHelper.getUserId()!!

        // 调用命令取消关注
        Mediator.commands.send(
            UnFocusCmd.Request(
                userId = currentUserId,
                focusUserId = focusUserId
            )
        )

        return UHomeCancelFocus.Response()
    }

    /**
     * 加载关注列表
     */
    @PostMapping("/loadFocusList")
    fun uHomeLoadFocusList(@RequestBody request: UHomeLoadFocusList.Request): PageData<UHomeLoadFocusList.UserItem> {
        val userId = LoginHelper.getUserId()!!

        // 调用查询获取关注列表
        val queryRequest = GetFocusListQry.Request(
            userId = userId
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        // 转换为前端需要的格式
        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { user ->
                UHomeLoadFocusList.UserItem(
                    userId = user.focusUserId.toString(),
                    nickName = user.nickName,
                    avatar = user.avatar,
                    fansCount = user.fansCount,
                    haveFocus = user.haveFocus
                )
            },
            totalCount = queryResult.totalCount
        )
    }

    /**
     * 加载粉丝列表
     */
    @PostMapping("/loadFansList")
    fun uHomeLoadFansList(@RequestBody request: UHomeLoadFansList.Request): PageData<UHomeLoadFansList.UserItem> {
        val userId = LoginHelper.getUserId()!!

        // 调用查询获取粉丝列表
        val queryRequest = GetFansListQry.Request(
            userId = userId
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        // 转换为前端需要的格式
        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { user ->
                UHomeLoadFansList.UserItem(
                    userId = user.userId.toString(),
                    nickName = user.nickName,
                    avatar = user.avatar,
                    fansCount = user.fansCount,
                    haveFocus = user.haveFocus
                )
            },
            totalCount = queryResult.totalCount
        )
    }

    /**
     * 加载用户视频列表
     */
    @PostMapping("/loadVideoList")
    fun uHomeLoadVideoList(@RequestBody @Validated request: UHomeLoadVideoList.Request): PageData<UHomeLoadVideoList.VideoItem> {
        // 构建查询请求，添加 userId 过滤
        val queryRequest = SearchVideosQry.Request(
            userId = request.userId?.toLong(),
            videoNameFuzzy = request.videoName,
            recommendType = null // 用户主页显示所有状态的视频
        ).apply {
            pageNum = request.pageNum
            pageSize = if (request.type != null) 10 else request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        // 转换为前端需要的格式
        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { video ->
                UHomeLoadVideoList.VideoItem(
                    videoId = video.videoId.toString(),
                    videoCover = video.videoCover,
                    videoName = video.videoName,
                    createTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(video.createTime),
                        ZoneId.systemDefault()
                    ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    playCount = video.playCount,
                    likeCount = video.likeCount,
                    danmuCount = video.danmuCount,
                    commentCount = video.commentCount
                )
            },
            totalCount = queryResult.totalCount
        )
    }

    /**
     * 加载用户收藏列表
     */
    @PostMapping("/loadUserCollection")
    fun uHomeLoadUserCollection(@RequestBody @Validated request: UHomeLoadUserCollection.Request): PageData<UHomeLoadUserCollection.VideoItem> {
        val customerId = LoginHelper.getUserId()!!

        // 调用查询获取用户收藏的视频ID列表
        val collectionRequest = CustomerCollectedVideoIdsQry.Request(
            customerId = customerId
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val collectedVideoIds = Mediator.queries.send(collectionRequest)

        // 如果没有收藏的视频，直接返回空列表
        if (collectedVideoIds.list.isEmpty()) {
            return PageData.create(
                pageNum = request.pageNum,
                pageSize = request.pageSize,
                list = emptyList(),
                totalCount = 0
            )
        }

        // 根据视频ID列表查询视频详情
        val videoIds = collectedVideoIds.list.map { it.videoId }
        val videosRequest = SearchVideosQry.Request(
            userId = null,
            videoNameFuzzy = null,
            recommendType = null
        ).apply {
            pageNum = 1
            pageSize = videoIds.size
        }

        val videosResult = Mediator.queries.send(videosRequest)
        val videoMap = videosResult.list.filter { videoIds.contains(it.videoId) }
            .associateBy { it.videoId }

        // 转换为前端需要的格式
        return PageData.create(
            pageNum = collectionRequest.pageNum,
            pageSize = collectionRequest.pageSize,
            list = collectedVideoIds.list.mapNotNull { collected ->
                videoMap[collected.videoId]?.let { video ->
                    UHomeLoadUserCollection.VideoItem(
                        videoId = video.videoId.toString(),
                        videoCover = video.videoCover,
                        videoName = video.videoName,
                        collectTime = LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(video.createTime),
                            ZoneId.systemDefault()
                        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        playCount = video.playCount,
                        likeCount = video.likeCount,
                    )
                }
            },
            totalCount = collectedVideoIds.totalCount
        )
    }

}
