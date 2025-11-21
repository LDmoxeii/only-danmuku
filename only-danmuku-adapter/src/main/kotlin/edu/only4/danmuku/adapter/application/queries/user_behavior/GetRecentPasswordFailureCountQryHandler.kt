package edu.only4.danmuku.adapter.application.queries.user_behavior

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.user_behavior.GetRecentPasswordFailureCountQry
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginResult
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginType
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.ge
import org.babyfish.jimmer.sql.kt.ast.expression.le
import org.springframework.stereotype.Service

/**
 * 统计用户在指定时间窗口内的密码失败次数
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
@Service
class GetRecentPasswordFailureCountQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetRecentPasswordFailureCountQry.Request, GetRecentPasswordFailureCountQry.Response> {

    override fun exec(request: GetRecentPasswordFailureCountQry.Request): GetRecentPasswordFailureCountQry.Response {
        val now = request.now ?: (System.currentTimeMillis() / 1000L)
        val start = now - request.windowSeconds

        val count = sqlClient.createQuery(UserLoginLog::class) {
            where(table.loginType eq LoginType.PASSWORD)
            where(table.result eq LoginResult.FAILURE)
            where(table.occurTime ge start)
            where(table.occurTime le now)
            where(request.userId?.let { table.userId eq it } ?: (table.loginName eq request.loginName))
            select(count(table.id))
        }.fetchOne()

        return GetRecentPasswordFailureCountQry.Response(failureCount = count)
    }
}
