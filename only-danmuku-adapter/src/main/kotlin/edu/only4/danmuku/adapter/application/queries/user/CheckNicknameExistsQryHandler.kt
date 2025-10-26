package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.User
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.nickName
import edu.only4.danmuku.application.queries.user.CheckNicknameExistsQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.`ne?`
import org.babyfish.jimmer.sql.kt.exists
import org.springframework.stereotype.Service

/**
 * 检查昵称是否存在
 */
@Service
class CheckNicknameExistsQryHandler(
    private val sqlClient: KSqlClient,
) : Query<CheckNicknameExistsQry.Request, CheckNicknameExistsQry.Response> {

    override fun exec(request: CheckNicknameExistsQry.Request): CheckNicknameExistsQry.Response {
        val exists = sqlClient.exists(User::class) {
            where(table.nickName eq request.nickName)
            where(table.id `ne?` request.excludeUserId)
        }

        return CheckNicknameExistsQry.Response(
            exists = exists
        )
    }
}
