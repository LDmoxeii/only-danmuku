package edu.only4.danmuku.adapter.application.queries.customer_profile

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries._share.model.CustomerProfile
import edu.only4.danmuku.application.queries._share.model.email
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries.customer_profile.UniqueCustomerProfileEmailQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.`ne?`
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.exists

import org.springframework.stereotype.Service

/**
 * 用户信息;
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/04
 */
@Service
class UniqueCustomerProfileEmailQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueCustomerProfileEmailQry.Request, UniqueCustomerProfileEmailQry.Response> {

    override fun exec(request: UniqueCustomerProfileEmailQry.Request): UniqueCustomerProfileEmailQry.Response {
        val exists = sqlClient.exists(CustomerProfile::class) {
            where(table.email eq request.email)
            where(table.id `ne?` request.excludeCustomerProfileId)
        }

        return UniqueCustomerProfileEmailQry.Response(
            exists = exists
        )
    }
}

