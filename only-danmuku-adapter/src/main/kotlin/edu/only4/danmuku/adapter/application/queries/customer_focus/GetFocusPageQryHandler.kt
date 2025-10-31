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
 */
@Service
class GetFocusPageQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetFocusPageQry.Request, GetFocusPageQry.Response> {

    override fun exec(request: GetFocusPageQry.Request): PageData<GetFocusPageQry.Response> {

        // 查询当前用户的关注列表（我关注了谁）
        val pageResult =
            sqlClient.createQuery(CustomerFocus::class) {
                where(table.customerId eq request.userId)
                select(table.fetchBy {
                    focusCustomer {
                        relation {
                            nickName()
                            avatar()
                            personIntroduction()
                        }
                    }
                })
            }.fetchPage(request.pageNum - 1, request.pageSize)

        // 预取我的粉丝集合：哪些人关注了我，用于判断是否互关
        val myFansUserIds = sqlClient.findAll(CustomerFocusSimple::class) {
            where(table.focusCustomerId eq request.userId)
        }.map { it.customerId }.toSet()

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
                    nickName = focus.focusCustomer.relation!!.nickName,
                    avatar = focus.focusCustomer.relation!!.avatar,
                    personIntroduction = focus.focusCustomer.relation!!.personIntroduction,
                    fansCount = fansCount,
                    haveFocus = true,
                    focusType = if (myFansUserIds.contains(focus.focusCustomerId)) 1 else 0
                )
            },
            totalCount = pageResult.totalRowCount
        )
    }
}

