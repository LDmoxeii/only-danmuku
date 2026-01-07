package edu.only4.danmuku.adapter.portal.api.payload.video_series

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

object SaveVideoSeries {

    data class Request(
        val seriesId: Long?,
        @field:NotEmpty
        @field:Size(max = 100)
        val seriesName: String,
        @field:Size(max = 200)
        val seriesDescription: String?,
        val videoIds: String?,
    )

    data class Response(
        var seriesId: Long? = null
    )
}
