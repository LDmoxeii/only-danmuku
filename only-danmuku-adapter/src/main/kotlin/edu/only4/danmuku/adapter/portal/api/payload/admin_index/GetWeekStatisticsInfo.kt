package edu.only4.danmuku.adapter.portal.api.payload.admin_index

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import edu.only4.danmuku.application.queries.statistics.GetWeekStatisticsInfoQry
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * 获取周统计信息接口返回
 */
object GetWeekStatisticsInfo {

    data class Request(
        /** 数据类型 */
        val dataType: Int = 0
    )

    /**
     * 周统计数据项
     */
    data class Item(
        /** 对应日期（秒级时间戳），序列化为 yyyy-MM-dd 字符串 */
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd")
        var statisticsDate: Long,
        /** 统计数量 */
        var statisticsCount: Int,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        @Mapping(source = "date", target = "statisticsDate")
        @Mapping(source = "count", target = "statisticsCount")
        fun fromApp(resp: GetWeekStatisticsInfoQry.Response): Item

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
