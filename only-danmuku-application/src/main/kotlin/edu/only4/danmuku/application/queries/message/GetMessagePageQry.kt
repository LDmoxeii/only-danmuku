package edu.only4.danmuku.application.queries.message

import com.only4.cap4k.ddd.core.application.query.PageQueryParam
import edu.only4.danmuku.domain.aggregates.customer_message.extend.UserMessageExtend

/**
 * 获取消息分页
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/30
 */
object GetMessagePageQry {

    data class Request(
        var messageType: Int? = null,
    ) : PageQueryParam<Response>()

    data class Response(
        val id: Long,
        val messageType: Int,
        val readType: Int,
        val extendJson: UserMessageExtend?,
        val createTime: Long,
        // 扩展显示字段
        val videoPostId: Long? = null,
        val videoId: Long? = null,
        val videoName: String? = null,
        val videoCover: String? = null,
        val sendUserId: Long? = null,
        val sendUserName: String? = null,
        val sendUserAvatar: String? = null,
    )
}
