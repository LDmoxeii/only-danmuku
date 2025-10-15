package edu.only4.danmuku.adapter.application.queries.customer_message

import com.only4.cap4k.ddd.core.application.query.ListQuery

import edu.only4.danmuku.application.queries.customer_message.GetMessageListQry

import org.springframework.stereotype.Service

/**
 * 获取消息列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetMessageListQryHandler(
) : ListQuery<GetMessageListQry.Request, GetMessageListQry.Response> {

    override fun exec(request: GetMessageListQry.Request): List<GetMessageListQry.Response> {

        return listOf(GetMessageListQry.Response(

        ))

    }
}
