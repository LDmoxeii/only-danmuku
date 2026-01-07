package edu.only4.danmuku.adapter.portal.api.payload.u_center_statistics

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import edu.only4.danmuku.application.queries.statistics.GetWeekStatisticsInfoQry
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

object GetWeekStatisticsInfo {

    data class Request(
        val dataType: Int? = null
    )

    data class Item(
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd")
        var statisticsDate: Long,
        var statisticsCount: Int,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        @Mapping(source = "date", target = "statisticsDate")
        @Mapping(source = "count", target = "statisticsCount")
        fun fromApp(resp: GetWeekStatisticsInfoQry.Response): Item

        companion object { val INSTANCE: Converter = Mappers.getMapper(Converter::class.java) }
    }
}
