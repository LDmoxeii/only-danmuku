package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.User
import edu.only4.danmuku.application.queries._share.model.email
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries.user.CheckEmailExistsQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.`ne?`
import org.babyfish.jimmer.sql.kt.exists
import org.springframework.stereotype.Service

/**
 * 检查邮箱是否存在
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class CheckEmailExistsQryHandler(
    private val sqlClient: KSqlClient,
) : Query<CheckEmailExistsQry.Request, CheckEmailExistsQry.Response> {

    override fun exec(request: CheckEmailExistsQry.Request): CheckEmailExistsQry.Response {
        // 使用 Jimmer exists() 方法检查邮箱是否存在（性能最优）
        val exists = sqlClient.exists(User::class) {
            where(table.email eq request.email)
            where(table.id `ne?` request.excludeUserId)
        }

        return CheckEmailExistsQry.Response(
            exists = exists
        )
    }
}
