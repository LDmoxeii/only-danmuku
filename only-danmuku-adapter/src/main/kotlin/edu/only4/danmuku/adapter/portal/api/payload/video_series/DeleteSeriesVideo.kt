package edu.only4.danmuku.adapter.portal.api.payload.video_series

object DeleteSeriesVideo {

    data class Request(
        val seriesId: Long,
        val videoId: Long,
    )

    class Response
}
