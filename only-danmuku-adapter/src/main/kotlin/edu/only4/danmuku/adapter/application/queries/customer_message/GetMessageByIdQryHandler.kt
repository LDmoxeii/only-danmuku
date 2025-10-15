package edu.only4.danmuku.adapter.application.queries.customer_message

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.customer_message.GetMessageByIdQry

import org.springframework.stereotype.Service

/**
 * 根据ID获取消息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetMessageByIdQryHandler(
) : Query<GetMessageByIdQry.Request, GetMessageByIdQry.Response> {

    override fun exec(request: GetMessageByIdQry.Request): GetMessageByIdQry.Response {

        return GetMessageByIdQry.Response(

        )
    }
}
