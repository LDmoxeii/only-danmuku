package edu.only4.danmuku.adapter.application.distributed.clients

import com.only.engine.entity.UserInfo
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.application.RequestHandler
import edu.only4.danmuku.application.distributed.clients.RefreshLoginSessionCli
import edu.only4.danmuku.domain._share.meta.customer_profile.SCustomerProfile
import edu.only4.danmuku.domain._share.meta.user.SUser
import org.springframework.stereotype.Service

@Service
class RefreshLoginSessionCliHandler : RequestHandler<RefreshLoginSessionCli.Request, Unit> {

    override fun exec(request: RefreshLoginSessionCli.Request) {
        val currentUserId = LoginHelper.getUserId()
        if (currentUserId != null && currentUserId == request.userId) {
            val old = LoginHelper.getUserInfo()
            if (old != null) {
                val newUsername = request.nickName ?: old.username
                val newAvatar = request.avatar ?: (old.extra[SCustomerProfile.props.avatar] as? String ?: "")
                val relatedId = (old.extra[SUser.props.relatedId] as? Long) ?: request.userId
                LoginHelper.refreshUserInfo(
                    UserInfo(
                        request.userId,
                        old.userType,
                        newUsername,
                        extra = mapOf(
                            SCustomerProfile.props.avatar to newAvatar,
                            SUser.props.relatedId to relatedId
                        )
                    )
                )
            }
        }
    }
}

