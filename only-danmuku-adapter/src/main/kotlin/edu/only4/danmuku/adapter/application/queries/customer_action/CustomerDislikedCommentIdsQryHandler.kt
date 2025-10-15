package edu.only4.danmuku.adapter.application.queries.customer_action

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.customer_action.CustomerDislikedCommentIdsQry

import org.springframework.stereotype.Service

/**
 * 用户点踩的评论ID列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class CustomerDislikedCommentIdsQryHandler(
) : Query<CustomerDislikedCommentIdsQry.Request, CustomerDislikedCommentIdsQry.Response> {

    override fun exec(request: CustomerDislikedCommentIdsQry.Request): CustomerDislikedCommentIdsQry.Response {

        return CustomerDislikedCommentIdsQry.Response(

        )
    }
}
