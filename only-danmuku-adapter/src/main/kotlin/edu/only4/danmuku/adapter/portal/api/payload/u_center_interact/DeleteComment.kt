package edu.only4.danmuku.adapter.portal.api.payload.u_center_interact

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 加载弹幕列表(分页)接口载荷
 */
object DeleteComment {

    class Request(
        val commentId: Long
    )

    class Response

    @Mapper(componentModel = "default")
    interface Converter {

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
