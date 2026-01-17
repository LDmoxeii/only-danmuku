package edu.only4.danmuku.adapter.portal.api.payload.video_history

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.application.queries.video_play_history.GetUserPlayHistoryQry
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * 加载播放历史接口载荷
 */
object GetHistoryPage {

    class Request(
        /** 其他过滤条件可以在这里添加 */
    ) : PageParam()

    /**
     * 播放历史项
     */
    data class Item(
        /** 播放历史ID */
        var historyId: String? = null,
        /** 视频ID */
        var videoId: String? = null,
        /** 视频名称 */
        var videoName: String? = null,
        /** 视频封面 */
        var videoCover: String? = null,
        /** 文件索引 */
        var fileIndex: Int? = null,
        /** 播放时间 */
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss" )
        var playTime: Long? = null,
    )

    @Mapper(componentModel = "default")
    interface Converter {

        @Mapping(target = "customerId", source = "currentUserId")
        fun toQry(request: Request, currentUserId: Long): GetUserPlayHistoryQry.Request

        @Mapping(source = "historyId", target = "historyId")
        @Mapping(source = "videoId", target = "videoId")
        fun fromQry(resp: GetUserPlayHistoryQry.HistoryItem): Item

        companion object { val INSTANCE: Converter = Mappers.getMapper(Converter::class.java) }
    }
}
