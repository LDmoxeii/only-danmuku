package edu.only4.danmuku.application.queries.message

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取未读消息数(分组)
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/30
 */
object GetNoReadMessageCountGroupQry {

    class Request(

    ) : RequestParam<Response>

    data class Response(
        val list: List<Item> = emptyList(),
    ) {
        data class Item(
            val messageType: Int,
            val count: Int,
        )
    }
}
