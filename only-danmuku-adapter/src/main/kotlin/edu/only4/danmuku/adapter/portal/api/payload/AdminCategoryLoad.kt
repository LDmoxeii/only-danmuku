package edu.only4.danmuku.adapter.portal.api.payload

import edu.only4.danmuku.application.queries.category.GetCategoryTreeQry
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
/**
 * 加载分类列表(树形结构)接口载荷
 */
object AdminCategoryLoad {

    data class Item(
        var categoryId: Long,
        var categoryCode: String,
        var categoryName: String,
        var parentId: Long?,
        var icon: String?,
        var background: String?,
        var sort: Int,
        var children: List<Item>,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        @Mapping(source = "code", target = "categoryCode")
        @Mapping(source = "name", target = "categoryName")
        fun fromApp(node: GetCategoryTreeQry.Response): Item

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
