package edu.only4.danmuku.application.commands.customer_profile

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.NoneResultCommandParam
import edu.only4.danmuku.application.validator.NicknameChangeAllowed
import edu.only4.danmuku.application.validator.UniqueUserNickname
import edu.only4.danmuku.domain._share.meta.customer_profile.SCustomerProfile
import edu.only4.danmuku.domain.aggregates.customer_profile.enums.SexType
import edu.only4.danmuku.domain.aggregates.customer_profile.enums.ThemeType
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 更新用户信息
 */
object UpdateCustomerProfileCmd {

    @Service
    class Handler : NoneResultCommandParam<Request>() {
        override fun exec(request: Request) {
            val profile = Mediator.repositories.findFirst(
                SCustomerProfile.predicate { it.userId eq request.customerId },
            ).getOrNull() ?: throw KnownException("用户资料不存在：${request.customerId}")

            profile.updateProfileInfo(
                nickName = request.nickName,
                avatar = request.avatar,
                sex = request.sex?.let { SexType.valueOf(it) },
                birthday = request.birthday,
                school = request.school,
                personIntroduction = request.personIntroduction,
                noticeInfo = request.noticeInfo,
                theme = request.theme?.let { ThemeType.valueOf(it) }
            )

            Mediator.uow.save()
        }
    }

    @UniqueUserNickname(userIdField = "customerId", nicknameField = "nickName")
    @NicknameChangeAllowed(userIdField = "customerId", nicknameField = "nickName")
    data class Request(
        /** 用户ID */
        val customerId: Long,
        /** 昵称 */
        val nickName: String? = null,
        /** 头像 */
        val avatar: String? = null,
        /** 性别值，对应 SexType.code */
        val sex: Int? = null,
        /** 生日 */
        val birthday: String? = null,
        /** 学校 */
        val school: String? = null,
        /** 个人简介 */
        val personIntroduction: String? = null,
        /** 空间公告 */
        val noticeInfo: String? = null,
        /** 主题值，对应 ThemeType.code */
        val theme: Int? = null,
    ) : RequestParam<Unit>
}
