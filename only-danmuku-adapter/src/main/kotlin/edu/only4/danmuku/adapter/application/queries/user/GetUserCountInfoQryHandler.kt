package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.customer_focus.JCustomerFocus
import edu.only4.danmuku.application.queries._share.model.customer_focus.customerId
import edu.only4.danmuku.application.queries._share.model.customer_focus.focusCustomerId
import edu.only4.danmuku.application.queries._share.model.customer_profile.JCustomerProfile
import edu.only4.danmuku.application.queries._share.model.customer_profile.currentCoinCount
import edu.only4.danmuku.application.queries._share.model.customer_profile.id
import edu.only4.danmuku.application.queries.user.GetUserCountInfoQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取用户统计信息(粉丝数、硬币数、关注数)
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/20
 */
@Service
class GetUserCountInfoQryHandler(
    private val sqlClient: KSqlClient
) : Query<GetUserCountInfoQry.Request, GetUserCountInfoQry.Response> {

    override fun exec(request: GetUserCountInfoQry.Request): GetUserCountInfoQry.Response {
        // 1. 查询粉丝数 (有多少人关注了我)
        val fansCount = sqlClient
            .createQuery(JCustomerFocus::class) {
                where(table.focusCustomerId eq request.customerId)
                select(count(table))
            }
            .fetchOne()
            .toInt()

        // 2. 查询关注数 (我关注了多少人)
        val focusCount = sqlClient
            .createQuery(JCustomerFocus::class) {
                where(table.customerId eq request.customerId)
                select(count(table))
            }
            .fetchOne()
            .toInt()

        // 3. 查询当前硬币数
        val currentCoinCount = sqlClient
            .createQuery(JCustomerProfile::class) {
                where(table.id eq request.customerId)
                select(table.currentCoinCount)
            }
            .fetchOneOrNull() ?: throw IllegalArgumentException("用户信息不存在: ${request.customerId}")

        return GetUserCountInfoQry.Response(
            fansCount = fansCount,
            currentCoinCount = currentCoinCount,
            focusCount = focusCount
        )
    }
}
