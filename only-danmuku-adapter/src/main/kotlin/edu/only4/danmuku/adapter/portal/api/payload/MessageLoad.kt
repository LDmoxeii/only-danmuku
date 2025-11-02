package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam

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
        /** 扩展信息（已解析） */
        val extendDto: Any?,
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
