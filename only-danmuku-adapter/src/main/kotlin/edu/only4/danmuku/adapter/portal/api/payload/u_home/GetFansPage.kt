package edu.only4.danmuku.adapter.portal.api.payload.u_home

import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.application.queries.customer_focus.GetFansPageQry
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * 加载粉丝列表接口载荷
 */
object GetFansPage {

    class Request : PageParam()

    data class Item(
        // 兼容前端使用的字段命名
        var otherUserId: String? = null,
        var otherNickName: String? = null,
        var otherPersonIntroduction: String? = null,
        var otherAvatar: String? = null,
        var focusType: Int? = null,
    )

    @Mapper(componentModel = "default")
    interface Converter {

        fun toQry(request: Request, userId: Long): GetFansPageQry.Request

        @Mapping(source = "userId", target = "otherUserId")
        @Mapping(source = "nickName", target = "otherNickName")
        @Mapping(source = "personIntroduction", target = "otherPersonIntroduction")
        @Mapping(source = "avatar", target = "otherAvatar")
        fun fromQry(resp: GetFansPageQry.Response): Item

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
