package edu.only4.danmuku.adapter.portal.api.payload.video_history

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

object DeleteVideoHistory {

    class Request(
        val videoId: Long
    )

    class Response

    @Mapper(componentModel = "default")
    interface Converter {

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
