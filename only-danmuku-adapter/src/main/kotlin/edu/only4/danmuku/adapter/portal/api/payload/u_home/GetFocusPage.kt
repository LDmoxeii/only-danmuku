package edu.only4.danmuku.adapter.portal.api.payload.u_home

import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.application.queries.customer_focus.GetFocusPageQry
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * 加载关注列表接口载荷
 */
object GetFocusPage {

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

        fun toQry(request: Request, userId: Long): GetFocusPageQry.Request

        @Mapping(source = "focusUserId", target = "otherUserId")
        @Mapping(source = "nickName", target = "otherNickName")
        @Mapping(source = "personIntroduction", target = "otherPersonIntroduction")
        @Mapping(source = "avatar", target = "otherAvatar")
        fun fromApp(resp: GetFocusPageQry.Response): Item

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
