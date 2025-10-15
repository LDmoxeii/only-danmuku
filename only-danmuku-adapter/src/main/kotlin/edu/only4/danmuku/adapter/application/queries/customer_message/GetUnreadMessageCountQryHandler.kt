package edu.only4.danmuku.adapter.application.queries.customer_message

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.customer_message.GetUnreadMessageCountQry

import org.springframework.stereotype.Service

/**
 * 获取未读消息数量
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetUnreadMessageCountQryHandler(
) : Query<GetUnreadMessageCountQry.Request, GetUnreadMessageCountQry.Response> {

    override fun exec(request: GetUnreadMessageCountQry.Request): GetUnreadMessageCountQry.Response {

        return GetUnreadMessageCountQry.Response(

        )
    }
}
