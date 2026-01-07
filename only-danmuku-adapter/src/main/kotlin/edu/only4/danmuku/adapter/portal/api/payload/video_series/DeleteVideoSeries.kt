package edu.only4.danmuku.adapter.portal.api.payload.video_series

object DeleteVideoSeries {

    data class Request(
        val seriesId: Long
    )

    class Response
}
