package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.User
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.phone
import edu.only4.danmuku.application.queries.user.UniqueUserPhoneQry
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
class UniqueUserPhoneQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueUserPhoneQry.Request, UniqueUserPhoneQry.Response> {

    override fun exec(request: UniqueUserPhoneQry.Request): UniqueUserPhoneQry.Response {
        val exists = sqlClient.exists(User::class) {
            where(table.phone eq request.phone)
            where(table.id `ne?` request.excludeUserId)
        }

        return UniqueUserPhoneQry.Response(
            exists = exists
        )
    }
}
