package edu.only4.danmuku.adapter.portal.api.payload.video

import edu.only4.danmuku.application.queries.video_file.GetVideoFilesByVideoIdQry
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

object GetVideoPList {

    data class Request(
        var videoId: Long
    )

    data class FileItem(
        var fileId: Long,
        var videoId: Long,
        var userId: Long,
        var fileIndex: Int,
        var fileName: String,
        var fileSize: Long,
        var filePath: String,
        var duration: Int
    )

    @Mapper(componentModel = "default")
    interface Converter {
        fun fromApp(resp: GetVideoFilesByVideoIdQry.Response): FileItem

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
