package edu.only4.danmuku.adapter.application.queries.customer_message

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.customer_message.GetUserMessagesQry

import org.springframework.stereotype.Service

/**
 * 获取用户消息列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetUserMessagesQryHandler(
) : Query<GetUserMessagesQry.Request, GetUserMessagesQry.Response> {

    override fun exec(request: GetUserMessagesQry.Request): GetUserMessagesQry.Response {

        return GetUserMessagesQry.Response(

        )
    }
}
