package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.customer_profile.JCustomerProfile
import edu.only4.danmuku.application.queries.user.CheckUserExistsQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.exists
import org.springframework.stereotype.Service
import edu.only4.danmuku.application.queries._share.model.customer_profile.id as customerId

@Service
class CheckUserExistsQryHandler(
    private val sqlClient: KSqlClient,
) : Query<CheckUserExistsQry.Request, CheckUserExistsQry.Response> {
    override fun exec(request: CheckUserExistsQry.Request): CheckUserExistsQry.Response {
        val exists = sqlClient.exists(JCustomerProfile::class) {
            where(table.customerId eq request.userId)
        }
        return CheckUserExistsQry.Response(exists = exists)
    }
}

