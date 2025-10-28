package edu.only4.danmuku.adapter.application.queries.customer_focus

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.model.CustomerFocus
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.dto.CustomerFocus.CustomerFocusSimple
import edu.only4.danmuku.application.queries._share.model.fetchBy
import edu.only4.danmuku.application.queries._share.model.focusCustomerId
import edu.only4.danmuku.application.queries.customer_focus.GetFocusPageQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取关注列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetFocusPageQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetFocusPageQry.Request, GetFocusPageQry.Response> {

    override fun exec(request: GetFocusPageQry.Request): PageData<GetFocusPageQry.Response> {

        // 查询当前用户的关注列表
        val pageResult =
            sqlClient.createQuery(CustomerFocus::class) {
                where(table.customerId eq request.userId)
                select(table.fetchBy {
                    customer {
                        relation {
                            nickName()
                            avatar()
                        }
                    }
                })
            }.fetchPage(request.pageNum - 1, request.pageSize)

        // 检查当前用户是否关注了这些人（在关注列表中，默认都是已关注）
        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = pageResult.rows.mapNotNull { focus ->
                // 统计该用户的粉丝数
                val fansCount = sqlClient.findAll(CustomerFocusSimple::class) {
                    where(table.focusCustomerId eq focus.focusCustomerId)
                }.size

                GetFocusPageQry.Response(
                    focusUserId = focus.focusCustomerId,
                    nickName = focus.customer.relation!!.nickName,
                    avatar = focus.customer.relation!!.avatar,
                    fansCount = fansCount,
                    haveFocus = true
                )
            },
            totalCount = pageResult.totalRowCount
        )
    }
}
