package edu.only4.danmuku.adapter.portal.api.payload

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.AnyToJsonStringTranslation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.domain.aggregates.customer_message.extend.UserMessageExtend

/**
 * 加载消息列表接口载荷
 */
object MessageLoad {

    data class Request(
        val messageType: Int?,

    ): PageParam()

    data class MessageItem(
        val id: Long,
        val messageType: Int,
        val readType: Int,
        @get:Translation(type = AnyToJsonStringTranslation.TYPE)
        val extendDto: UserMessageExtend?,
        val createTime: Long,
        // 扩展显示字段
        val videoId: Long?,
        val videoName: String?,
        val videoCover: String?,
        val sendUserId: Long?,
        val sendUserName: String?,
        val sendUserAvatar: String?,
    )
}
