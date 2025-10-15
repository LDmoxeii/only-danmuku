package edu.only4.danmuku.adapter.application.queries.customer_action

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.customer_action.CustomerLikedCommentIdsQry

import org.springframework.stereotype.Service

/**
 * 用户点赞的评论ID列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class CustomerLikedCommentIdsQryHandler(
) : Query<CustomerLikedCommentIdsQry.Request, CustomerLikedCommentIdsQry.Response> {

    override fun exec(request: CustomerLikedCommentIdsQry.Request): CustomerLikedCommentIdsQry.Response {

        return CustomerLikedCommentIdsQry.Response(

        )
    }
}
