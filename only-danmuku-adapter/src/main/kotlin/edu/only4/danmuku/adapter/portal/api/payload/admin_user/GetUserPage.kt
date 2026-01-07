package edu.only4.danmuku.adapter.portal.api.payload.admin_user

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.application.queries.customer_profile.GetCustomerProfilePageQry
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 加载用户列表(分页)接口载荷
 */
object GetUserPage {

    class Request(
        /** 昵称模糊查询 */
        val nickNameFuzzy: String? = null,
        /** 用户状态 */
        val status: Int? = null,
    ) : PageParam()

    /**
     * 用户项
     */
    data class Item(
        var userId: Long,
        var avatar: String?,
        var nickName: String?,
        var email: String?,
        var birthday: String?,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var joinTime: Long,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var lastLoginTime: Long?,
        var sex: Int?,
        var lastLoginIp: String?,
        var personIntroduction: String?,
        var currentCoinCount: Int?,
        var totalCoinCount: Int?,
        var status: Int?,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        fun toQry(request: Request): GetCustomerProfilePageQry.Request
        fun fromApp(resp: GetCustomerProfilePageQry.UserItem): Item

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
