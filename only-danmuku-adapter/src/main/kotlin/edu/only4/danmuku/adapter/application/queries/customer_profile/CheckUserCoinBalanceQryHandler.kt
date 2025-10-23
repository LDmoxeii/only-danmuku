package edu.only4.danmuku.adapter.application.queries.customer_profile

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.customer_profile.JCustomerProfile
import edu.only4.danmuku.application.queries._share.model.customer_profile.user
import edu.only4.danmuku.application.queries._share.model.user.id
import edu.only4.danmuku.application.queries.customer_profile.CheckUserCoinBalanceQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 检查用户硬币余额是否充足
 */
@Service
class CheckUserCoinBalanceQryHandler(
    private val sqlClient: KSqlClient,
) : Query<CheckUserCoinBalanceQry.Request, CheckUserCoinBalanceQry.Response> {

    override fun exec(request: CheckUserCoinBalanceQry.Request): CheckUserCoinBalanceQry.Response {
        val profiles = sqlClient.createQuery(JCustomerProfile::class) {
            where(table.user.id eq request.userId)
            select(table)
        }.execute()

        val profile = profiles.firstOrNull()
        val currentBalance = profile?.currentCoinCount ?: 0

        return CheckUserCoinBalanceQry.Response(
            sufficient = currentBalance >= request.requiredAmount,
            currentBalance = currentBalance
        )
    }
}
