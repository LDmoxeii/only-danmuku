package edu.only4.danmuku.adapter.portal.api.web

import cn.dev33.satoken.annotation.SaIgnore
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.u_home.BindPhone
import edu.only4.danmuku.adapter.portal.api.payload.u_home.CancelFocus
import edu.only4.danmuku.adapter.portal.api.payload.u_home.Focus
import edu.only4.danmuku.adapter.portal.api.payload.u_home.GetCustomerProfileDetail
import edu.only4.danmuku.adapter.portal.api.payload.u_home.GetFansPage
import edu.only4.danmuku.adapter.portal.api.payload.u_home.GetFocusPage
import edu.only4.danmuku.adapter.portal.api.payload.u_home.GetCollectionPage
import edu.only4.danmuku.adapter.portal.api.payload.u_home.GetVideoPage
import edu.only4.danmuku.adapter.portal.api.payload.u_home.SaveTheme
import edu.only4.danmuku.adapter.portal.api.payload.u_home.UpdateCustomerProfile
import edu.only4.danmuku.application.commands.customer_focus.FocusCmd
import edu.only4.danmuku.application.commands.customer_focus.UnFocusCmd
import edu.only4.danmuku.application.commands.customer_profile.UpdateCustomerProfileCmd
import edu.only4.danmuku.application.commands.customer_profile.BindPhoneCmd
import edu.only4.danmuku.application.queries.customer_focus.CheckFocusStatusQry
import edu.only4.danmuku.application.queries.customer_profile.GetCustomerProfileQry
import edu.only4.danmuku.application.queries.statistics.GetTotalStatisticsInfoQry
import edu.only4.danmuku.domain._share.meta.user.SUser
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/uHome")
class UHomeController {

    @SaIgnore
    @PostMapping("/customerProfile/detail")
    fun getCustomerProfileDetail(@RequestBody @Validated request: GetCustomerProfileDetail.Request): GetCustomerProfileDetail.Response {

        val queryResult = Mediator.queries.send(
            GetCustomerProfileQry.Request(
                customerId = request.userId
            )
        )

        val stats = Mediator.queries.send(
            GetTotalStatisticsInfoQry.Request(userId = request.userId)
        )

        val haveFocus = LoginHelper.getUserId()?.let {
            if (it != request.userId) {
                Mediator.queries.send(
                    CheckFocusStatusQry.Request(
                        userId = it,
                        focusUserId = request.userId
                    )
                ).haveFocus
            } else false
        } ?: false

        return GetCustomerProfileDetail.Response(
            userId = queryResult.customerId,
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

    @PostMapping("/customerProfile/update")
    fun update(@RequestBody @Validated request: UpdateCustomerProfile.Request) =
        Mediator.commands.send(
            UpdateCustomerProfileCmd.Request(
                customerId = LoginHelper.getUserId()!!,
                nickName = request.nickName,
                avatar = request.avatar,
                sex = request.sex,
                birthday = request.birthday,
                school = request.school,
                personIntroduction = request.personIntroduction,
                noticeInfo = request.noticeInfo
            )
        )

    @PostMapping("/saveTheme")
    fun saveTheme(@RequestBody @Validated request: SaveTheme.Request) =
        Mediator.commands.send(
            UpdateCustomerProfileCmd.Request(
                customerId = LoginHelper.getUserId()!!,
                theme = request.theme
            )
        )

    @PostMapping("/focus")
    fun focus(@RequestBody @Validated  request: Focus.Request) =
        Mediator.commands.send(
            FocusCmd.Request(
                userId = LoginHelper.getUserId()!!,
                focusUserId = request.focusUserId
            )
        )

    @PostMapping("/cancelFocus")
    fun cancelFocus(@RequestBody @Validated  request: CancelFocus.Request) =
        Mediator.commands.send(
            UnFocusCmd.Request(
                userId = LoginHelper.getUserId()!!,
                focusUserId = request.focusUserId
            )
        )

    @PostMapping("/getFocusPage")
    fun getFocusPage(@RequestBody @Validated request: GetFocusPage.Request): PageData<GetFocusPage.Item> {
        val userId = LoginHelper.getUserId()!!

        val queryResult = Mediator.queries.send(GetFocusPage.Converter.INSTANCE.toQry(request, userId))

        // 转换为前端需要的格式
        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { GetFocusPage.Converter.INSTANCE.fromApp(it) },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/getFansPage")
    fun getFansPage(@RequestBody @Validated request: GetFansPage.Request): PageData<GetFansPage.Item> {
        val userId = LoginHelper.getUserId()!!

        val queryResult = Mediator.queries.send(GetFansPage.Converter.INSTANCE.toQry(request, userId))

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { GetFansPage.Converter.INSTANCE.fromQry(it) },
            totalCount = queryResult.totalCount
        )
    }

    @SaIgnore
    @PostMapping("/getVideoPage")
    fun getVideoPage(@RequestBody @Validated request: GetVideoPage.Request): PageData<GetVideoPage.Item> {
        val queryResult = Mediator.queries.send(GetVideoPage.Converter.INSTANCE.toQry(request))

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { GetVideoPage.Converter.INSTANCE.fromQry(it) },
            totalCount = queryResult.totalCount
        )
    }

    @SaIgnore
    @PostMapping("/getCollectionPage")
    fun getCollectionPage(@RequestBody @Validated request: GetCollectionPage.Request): PageData<GetCollectionPage.Item> {
        val currentUserId = request.userId ?: LoginHelper.getUserId()!!

        val collectedActions = Mediator.queries.send(GetCollectionPage.Converter.INSTANCE.toQry(request, currentUserId))

        // 如果没有收藏的视频，直接返回空列表
        if (collectedActions.list.isEmpty()) {
            return PageData.empty()
        }

        // 转换为前端需要的格式（与注释示例保持一致）
        return PageData.create(
            pageNum = collectedActions.pageNum,
            pageSize = collectedActions.pageSize,
            list = collectedActions.list.map { GetCollectionPage.Converter.INSTANCE.fromApp(it) },
            totalCount = collectedActions.totalCount
        )
    }

    @PostMapping("/bindPhone")
    fun bindPhone(@RequestBody @Validated request: BindPhone.Request) {
        // TODO：由于政策原因，暂时屏蔽短信验证码校验
//        val smsCheck = Mediator.requests.send(
//            CaptchaValidCli.Request(request.captchaId, request.smsCode)
//        )
//        require(smsCheck.result) { "短信验证码错误" }

        val customerProfileId = LoginHelper.getUserInfo()!!.extra.getValue(SUser.props.relatedId) as Long

        Mediator.commands.send(
            BindPhoneCmd.Request(
                customerProfileId = customerProfileId,
                phone = request.phone,
            )
        )
    }

}


