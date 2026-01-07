package edu.only4.danmuku.adapter.portal.api.payload.admin_category

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 保存/更新分类接口载荷
 */
object DeleteCategory {

    data class Request(
        val categoryId: Long
    )

    class Response

    @Mapper(componentModel = "default")
    interface Converter {
        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
