package edu.only4.danmuku.adapter.portal.api.payload.video_series

import jakarta.validation.constraints.NotEmpty

object SaveSeriesVideo {

    data class Request(
        val seriesId: Long,
        @field:NotEmpty
        val videoIds: String,
    )

    class Response
}
