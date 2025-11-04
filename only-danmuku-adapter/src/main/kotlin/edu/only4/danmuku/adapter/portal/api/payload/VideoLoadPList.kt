package edu.only4.danmuku.adapter.portal.api.payload

object VideoLoadPList {

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

    @org.mapstruct.Mapper(componentModel = "default")
    interface Converter {
        fun fromApp(resp: edu.only4.danmuku.application.queries.video_file.GetVideoFilesByVideoIdQry.Response): FileItem

        companion object {
            val INSTANCE: Converter = org.mapstruct.factory.Mappers.getMapper(Converter::class.java)
        }
    }
}
