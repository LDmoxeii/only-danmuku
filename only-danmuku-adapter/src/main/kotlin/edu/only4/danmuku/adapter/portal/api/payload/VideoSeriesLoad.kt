package edu.only4.danmuku.adapter.portal.api.payload

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import edu.only4.danmuku.application.queries.customer_video_series.GetCustomerVideoSeriesListQry
import jakarta.validation.constraints.NotEmpty
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * 加载视频系列列表接口载荷
 */
object VideoSeriesLoad {

    data class Request(
        /** 用户ID */
        @field:NotEmpty(message = "用户ID不能为空")
        val userId: String = ""
    )

    data class Response(
        /** 系列列表 */
        var list: List<SeriesItem>? = null
    )

    data class SeriesItem(
        var seriesId: String? = null,
        var seriesName: String? = null,
        var seriesDescription: String? = null,
        var sort: Int? = null,
        var videoCount: Int? = null,
        var cover: String? = null,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var createTime: Long? = null,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var updateTime: Long? = null,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        @Mapping(source = "seriesId", target = "seriesId")
        fun fromApp(resp: GetCustomerVideoSeriesListQry.Response): SeriesItem

        companion object { val INSTANCE: Converter = Mappers.getMapper(Converter::class.java) }
    }
}

