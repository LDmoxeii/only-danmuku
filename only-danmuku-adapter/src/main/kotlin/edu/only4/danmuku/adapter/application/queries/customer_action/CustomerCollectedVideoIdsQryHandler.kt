package edu.only4.danmuku.adapter.application.queries.customer_action

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.draft.customer_action.CustomerActionSimple
import edu.only4.danmuku.application.queries._share.model.customer_action.actionType
import edu.only4.danmuku.application.queries._share.model.customer_action.customerId
import edu.only4.danmuku.application.queries.customer_action.CustomerCollectedVideoIdsQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 用户收藏的视频ID列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class CustomerCollectedVideoIdsQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<CustomerCollectedVideoIdsQry.Request, CustomerCollectedVideoIdsQry.Response> {

    override fun exec(request: CustomerCollectedVideoIdsQry.Request): PageData<CustomerCollectedVideoIdsQry.Response> {

        // 查询用户收藏的视频（actionType = 4 表示视频收藏）
        val pageResult =
            sqlClient.createQuery(edu.only4.danmuku.application.queries._share.model.customer_action.JCustomerAction::class) {
                where(table.customerId eq request.customerId)
                where(table.actionType eq 4.toByte())  // FAVORITE_VIDEO
                select(table.fetch(CustomerActionSimple::class))
            }.fetchPage(request.pageNum - 1, request.pageSize)

        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = pageResult.rows.map { action ->
                CustomerCollectedVideoIdsQry.Response(
                    videoId = action.videoId
                )
            },
            totalCount = pageResult.totalRowCount
        )
    }
}
