package edu.only4.danmuku.adapter.portal.api.compatible

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.*
import edu.only4.danmuku.application.commands.customer_focus.FocusCmd
import edu.only4.danmuku.application.commands.customer_focus.UnFocusCmd
import edu.only4.danmuku.application.commands.customer_profile.UpdateCustomerProfileCmd
import edu.only4.danmuku.application.queries.customer_action.GetCollectionPageQry
import edu.only4.danmuku.application.queries.customer_focus.CheckFocusStatusQry
import edu.only4.danmuku.application.queries.customer_focus.GetFansListQry
import edu.only4.danmuku.application.queries.customer_focus.GetFocusPageQry
import edu.only4.danmuku.application.queries.customer_profile.GetCustomerProfileQry
import edu.only4.danmuku.application.queries.statistics.GetTotalStatisticsInfoQry
import edu.only4.danmuku.application.queries.video.GetVideoPageQry
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/uhome")
@Validated
class CompatibleUHomeController {

    @SaIgnore
    @PostMapping("/getUserInfo")
    fun getCustomerProfile(userId: Long): UHomeGetUserInfo.UserInfo {

        val queryResult = Mediator.queries.send(
            GetCustomerProfileQry.Request(
                customerId = userId
            )
        )

        val stats = Mediator.queries.send(
            GetTotalStatisticsInfoQry.Request(userId = userId)
        )

        val haveFocus = LoginHelper.getUserId()?.let {
            if (it != userId) {
                Mediator.queries.send(
                    CheckFocusStatusQry.Request(
                        userId = it,
                        focusUserId = userId
                    )
                ).haveFocus
            } else false
        } ?: false

        return UHomeGetUserInfo.UserInfo(
            userId = queryResult.customerId.toString(),
            nickName = queryResult.nickName,
            avatar = queryResult.avatar,
            sex = queryResult.sex,
            birthday = queryResult.birthday,
            school = queryResult.school,
            personIntroduction = queryResult.personIntroduction,
            noticeInfo = queryResult.noticeInfo,
            grade = null,
            theme = queryResult.theme,
            currentCoinCount = queryResult.currentCoinCount,
            fansCount = queryResult.fansCount,
            focusCount = queryResult.focusCount,
            likeCount = stats.likeCount,
            playCount = stats.playCount,
            haveFocus = haveFocus
        )
    }

    @PostMapping("/updateUserInfo")
    fun updateCustomerProfile(
        @NotEmpty @Size(max = 20) nickName: String,
        @NotEmpty @Size(max = 100) avatar: String,
        sex: Int, birthday: String?,
        @Size(max = 150) school: String?,
        @Size(max = 80) personIntroduction: String?,
        @Size(max = 300) noticeInfo: String?,
    ) {
        val userId = LoginHelper.getUserId()!!
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
    }

    @PostMapping("/saveTheme")
    fun uHomeSaveTheme(
        theme: Int
    ) {
        val userId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            UpdateCustomerProfileCmd.Request(
                customerId = userId,
                theme = theme
            )
        )
    }

    @PostMapping("/focus")
    fun uHomeFocus(focusUserId: Long) {
        val currentUserId = LoginHelper.getUserId()!!

        Mediator.commands.send(
            FocusCmd.Request(
                userId = currentUserId,
                focusUserId = focusUserId
            )
        )
    }

    /**
     * 取消关注
     */
    @PostMapping("/cancelFocus")
    fun uHomeCancelFocus(focusUserId: Long) {
        val currentUserId = LoginHelper.getUserId()!!

        // 调用命令取消关注
        Mediator.commands.send(
            UnFocusCmd.Request(
                userId = currentUserId,
                focusUserId = focusUserId
            )
        )
    }

    @PostMapping("/loadFocusList")
    fun getFocusPage(request: UHomeLoadFocusList.Request): PageData<UHomeLoadFocusList.UserItem> {
        val userId = LoginHelper.getUserId()!!

        val queryRequest = GetFocusPageQry.Request(
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
            list = queryResult.list.map { UHomeLoadFocusList.Converter.INSTANCE.fromApp(it) },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/loadFansList")
    fun getFansPage(request: UHomeLoadFansList.Request): PageData<UHomeLoadFansList.UserItem> {
        val userId = LoginHelper.getUserId()!!

        val queryRequest = GetFansListQry.Request(
            userId = userId
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { UHomeLoadFansList.Converter.INSTANCE.fromApp(it) },
            totalCount = queryResult.totalCount
        )
    }

    @SaIgnore
    @PostMapping("/loadVideoList")
    fun getVideoPage(@Validated request: UHomeLoadVideoList.Request): PageData<UHomeLoadVideoList.VideoItem> {
        val queryRequest = GetVideoPageQry.Request(
            userId = request.userId,
            videoNameFuzzy = request.videoName,
        ).apply {
            pageNum = request.pageNum
            pageSize = if (request.type != null) 10 else request.pageSize
            sort.add(
                when (request.orderType) {
                    0 -> UHomeLoadVideoList.Request.CREATE_TIME_DESC
                    1 -> UHomeLoadVideoList.Request.PLAY_COUNT_DESC
                    2 -> UHomeLoadVideoList.Request.COLLECT_COUNT_DESC
                    else -> UHomeLoadVideoList.Request.CREATE_TIME_DESC
                }
            )
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { UHomeLoadVideoList.Converter.INSTANCE.fromApp(it) },
            totalCount = queryResult.totalCount
        )
    }

    @SaIgnore
    @PostMapping("/loadUserCollection")
    fun getCollectionPage(@Validated request: UHomeLoadUserCollection.Request): PageData<UHomeLoadUserCollection.VideoItem> {
        val actualUserId = request.userId ?: LoginHelper.getUserId()!!

        // 调用查询获取用户收藏的视频ID列表
        val collectionRequest = GetCollectionPageQry.Request(
            customerId = actualUserId
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val collectedActions = Mediator.queries.send(collectionRequest)

        // 如果没有收藏的视频，直接返回空列表
        if (collectedActions.list.isEmpty()) {
            return PageData.empty()
        }

        // 转换为前端需要的格式（与注释示例保持一致）
        return PageData.create(
            pageNum = collectionRequest.pageNum,
            pageSize = collectionRequest.pageSize,
            list = collectedActions.list.map { UHomeLoadUserCollection.Converter.INSTANCE.fromApp(it) },
            totalCount = collectedActions.totalCount
        )
    }

}


