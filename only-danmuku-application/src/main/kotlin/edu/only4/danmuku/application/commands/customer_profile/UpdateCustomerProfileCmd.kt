package edu.only4.danmuku.application.commands.customer_profile

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.UniqueUserNickname
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
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val profile = Mediator.repositories.findFirst(
                SCustomerProfile.predicate { it.userId eq request.customerId },
                persist = false
            ).getOrNull() ?: throw KnownException("用户资料不存在：${request.customerId}")

            // TODO
            // 需要判断是否有足够的硬币修改昵称，
            // 如果修改了昵称，需要发出事件，并触发扣减硬币命令
            // 如果修改了昵称，还需要触发修改token的命令
            // 如果修改头像信息，也需要出发修改token的命令
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
            return Response()
        }
    }

    @UniqueUserNickname(userIdField = "customerId", nicknameField = "nickName")
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
    ) : RequestParam<Response>

    class Response
}
