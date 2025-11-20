package edu.only4.danmuku.adapter.application.queries.customer_profile

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries._share.model.CustomerProfile
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.nickName
import edu.only4.danmuku.application.queries.customer_profile.UniqueCustomerProfileNickNameQry
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
class UniqueCustomerProfileNickNameQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueCustomerProfileNickNameQry.Request, UniqueCustomerProfileNickNameQry.Response> {

    override fun exec(request: UniqueCustomerProfileNickNameQry.Request): UniqueCustomerProfileNickNameQry.Response {
        val exists = sqlClient.exists(CustomerProfile::class) {
            where(table.nickName eq request.nickName)
            where(table.id `ne?` request.excludeCustomerProfileId)
        }

        return UniqueCustomerProfileNickNameQry.Response(
            exists = exists
        )
    }
}

