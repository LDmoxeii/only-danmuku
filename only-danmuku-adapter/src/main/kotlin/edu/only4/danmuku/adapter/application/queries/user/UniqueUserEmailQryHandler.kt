package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries._share.model.User
import edu.only4.danmuku.application.queries._share.model.email
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries.user.UniqueUserEmailQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.`ne?`
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.exists

import org.springframework.stereotype.Service

/**
 * 帐号;
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/04
 */
@Service
class UniqueUserEmailQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueUserEmailQry.Request, UniqueUserEmailQry.Response> {

    override fun exec(request: UniqueUserEmailQry.Request): UniqueUserEmailQry.Response {
        val exists = sqlClient.exists(User::class) {
            where(table.email eq request.email)
            where(table.id `ne?` request.excludeUserId)
        }

        return UniqueUserEmailQry.Response(
            exists = exists
        )
    }
}

