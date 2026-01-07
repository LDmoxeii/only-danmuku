package edu.only4.danmuku.adapter.portal.api.payload.user_message

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.AnyToJsonStringTranslation
import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.application.queries.message.GetMessagePageQry
import edu.only4.danmuku.domain.aggregates.customer_message.extend.UserMessageExtend
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * 加载消息列表接口载荷
 */
object GetMessagePage {

    data class Request(
        val messageType: Int?,
    ): PageParam()

    data class Item(
        val messageId: Long,
        val messageType: Int,
        val readType: Int,
        @get:Translation(type = AnyToJsonStringTranslation.TYPE)
        val extendDto: UserMessageExtend?,
        val createTime: Long,
        // 扩展显示字段
        val videoPostId: Long?,
        val videoId: Long?,
        val videoName: String?,
        val videoCover: String?,
        val sendUserId: Long?,
        val sendUserName: String?,
        val sendUserAvatar: String?,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        @Mapping(source = "extendJson", target = "extendDto")
        @Mapping(source = "id", target = "messageId")
        fun fromApp(resp: GetMessagePageQry.Response): Item

        companion object { val INSTANCE: Converter = Mappers.getMapper(Converter::class.java) }
    }
}
