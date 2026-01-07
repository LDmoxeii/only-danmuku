package edu.only4.danmuku.adapter.portal.api.payload.category

import edu.only4.danmuku.application.queries.category.GetCategoryTreeQry
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * 加载所有分类接口载荷
 */
object GetCategoryTree {

    class Request

    data class Item(
        var categoryId: Long,
        var categoryCode: String,
        var categoryName: String,
        var parentCategoryId: Long,
        var icon: String?,
        var background: String?,
        var sort: Int,
        var children: List<Item>,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        @Mapping(source = "code", target = "categoryCode")
        @Mapping(source = "name", target = "categoryName")
        @Mapping(source = "parentId", target = "parentCategoryId")
        fun fromApp(node: GetCategoryTreeQry.Response): Item

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
