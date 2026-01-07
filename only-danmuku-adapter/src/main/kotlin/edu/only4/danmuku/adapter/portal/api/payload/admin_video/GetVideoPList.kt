package edu.only4.danmuku.adapter.portal.api.payload.admin_video

import edu.only4.danmuku.application.queries.video.GetVideoPlayFilesQry
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 加载视频分片列表接口载荷
 */
object GetVideoPList {

    data class Request(
        /** 视频ID */
        val videoId: Long
    )

    /**
     * 视频文件分片项
     */
    data class Item(
        /** 文件ID */
        var fileId: String? = null,
        /** 视频ID */
        var videoId: String? = null,
        /** 分片索引 */
        var fileIndex: Int? = null,
        /** 文件名称 */
        var fileName: String? = null,
        /** 文件大小 */
        var fileSize: Long? = null,
        /** 文件路径 */
        var filePath: String? = null,
        /** 时长(秒) */
        var duration: Int? = null,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        fun fromApp(resp: GetVideoPlayFilesQry.Response): Item

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
