package edu.only4.danmuku.adapter.application.queries.customer_focus

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.model.CustomerFocus
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.dto.CustomerFocus.CustomerFocusSimple
import edu.only4.danmuku.application.queries._share.model.fetchBy
import edu.only4.danmuku.application.queries._share.model.focusCustomerId
import edu.only4.danmuku.application.queries.customer_focus.GetFansListQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取粉丝列表
 */
@Service
class GetFansListQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetFansListQry.Request, GetFansListQry.Response> {

    override fun exec(request: GetFansListQry.Request): PageData<GetFansListQry.Response> {

        // 查询当前用户的粉丝列表（谁关注了我）
        val pageResult =
            sqlClient.createQuery(CustomerFocus::class) {
                where(table.focusCustomerId eq request.userId)
                select(table.fetchBy {
                    customer {
                        relation {
                            nickName()
                            avatar()
                            personIntroduction()
                        }
                    }
                    focusCustomerId()
                })
            }.fetchPage(request.pageNum - 1, request.pageSize)

        // 我关注过的用户集合：用于判断互关
        val myFocusUserIds = sqlClient.findAll(CustomerFocusSimple::class) {
            where(table.customerId eq request.userId)
            select(table.focusCustomerId)
        }.map { it.focusCustomerId }.toSet()

        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = pageResult.rows.mapNotNull { focus ->
                // 统计该用户的粉丝数
                val fansCount = sqlClient.findAll(CustomerFocusSimple::class) {
                    where(table.focusCustomerId eq focus.customerId)
                }.size
                GetFansListQry.Response(
                    userId = focus.customerId,
                    nickName = focus.customer.relation!!.nickName,
                    avatar = focus.customer.relation!!.avatar,
                    personIntroduction = focus.customer.relation!!.personIntroduction,
                    fansCount = fansCount,
                    haveFocus = myFocusUserIds.contains(focus.customerId),
                    focusType = if (myFocusUserIds.contains(focus.customerId)) 1 else 0
                )
            },
            totalCount = pageResult.totalRowCount
        )
    }
}

