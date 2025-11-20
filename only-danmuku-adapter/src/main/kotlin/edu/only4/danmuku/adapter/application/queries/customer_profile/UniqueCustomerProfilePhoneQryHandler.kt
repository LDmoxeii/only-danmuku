package edu.only4.danmuku.adapter.application.queries.customer_profile

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries._share.model.CustomerProfile
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.phone
import edu.only4.danmuku.application.queries.customer_profile.UniqueCustomerProfilePhoneQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.`ne?`
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.exists

import org.springframework.stereotype.Service

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
@Service
class UniqueCustomerProfilePhoneQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueCustomerProfilePhoneQry.Request, UniqueCustomerProfilePhoneQry.Response> {

    override fun exec(request: UniqueCustomerProfilePhoneQry.Request): UniqueCustomerProfilePhoneQry.Response {
        val exists = sqlClient.exists(CustomerProfile::class) {
            where(table.phone eq request.phone)
            where(table.id `ne?` request.excludeCustomerProfileId)
        }

        return UniqueCustomerProfilePhoneQry.Response(
            exists = exists
        )
    }
}

