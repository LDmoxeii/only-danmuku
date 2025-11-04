package edu.only4.danmuku.adapter.portal.api.payload

import edu.only4.danmuku.application.queries.message.GetNoReadMessageCountGroupQry
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
/**
 * 获取未读消息数(分组)接口载荷
 */
object MessageGetNoReadCountGroup {

    class Request

    data class Response(
        /** 分组未读消息数列表 */
        var list: List<GroupItem>? = null
    )

    data class GroupItem(
        val messageType: Int,
        val messageCount: Int,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        @Mapping(source = "count", target = "messageCount")
        fun fromApp(item: GetNoReadMessageCountGroupQry.Response.Item): GroupItem

        companion object { val INSTANCE: Converter = Mappers.getMapper(Converter::class.java) }
    }
}
