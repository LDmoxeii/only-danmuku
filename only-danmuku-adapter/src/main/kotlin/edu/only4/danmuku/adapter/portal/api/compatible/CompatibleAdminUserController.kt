package edu.only4.danmuku.adapter.portal.api.compatible

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.adapter.portal.api.payload.AdminUserLoad
import edu.only4.danmuku.application.commands.user.ChangeUserStatusCmd
import edu.only4.danmuku.application.queries.customer_profile.GetCustomerProfilePageQry
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@RestController
@RequestMapping("/admin/user")
@Validated
class CompatibleAdminUserController {

    @PostMapping("/loadUser")
    fun getUserPage(@RequestBody request: AdminUserLoad.Request): PageData<AdminUserLoad.UserItem> {
        val queryRequest = GetCustomerProfilePageQry.Request(
            nickNameFuzzy = request.nickNameFuzzy,
            status = request.status
        ).apply {
            pageNum = request.pageNum
            pageSize = request.pageSize
        }

        val queryResult = Mediator.queries.send(queryRequest)

        return PageData.create(
            pageNum = queryResult.pageNum,
            pageSize = queryResult.pageSize,
            list = queryResult.list.map { user ->
                AdminUserLoad.UserItem(
                    userId = user.userId.toString(),
                    email = user.email,
                    nickName = user.nickName,
                    avatar = user.avatar,
                    sex = user.sex,
                    birthday = user.birthday,
                    school = user.school,
                    personalSignature = user.personalSignature,
                    status = user.status,
                    joinTime = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(user.joinTime),
                        ZoneId.systemDefault()
                    ),
                    lastLoginTime = user.lastLoginTime?.let {
                        LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(it),
                            ZoneId.systemDefault()
                        )
                    },
                    currentCoinCount = user.currentCoinCount,
                    theme = user.theme
                )
            },
            totalCount = queryResult.totalCount
        )
    }

    @PostMapping("/changeStatus")
    fun changeUserStatus(userId: Long, status: Int) {
        val status = status == 1

        Mediator.commands.send(
            ChangeUserStatusCmd.Request(
                userId = userId,
                status = status
            )
        )
    }

}
