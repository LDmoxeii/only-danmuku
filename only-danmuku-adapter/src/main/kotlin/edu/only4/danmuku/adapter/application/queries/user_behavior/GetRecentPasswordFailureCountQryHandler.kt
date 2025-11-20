package edu.only4.danmuku.adapter.application.queries.user_behavior

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.user_behavior.GetRecentPasswordFailureCountQry
import edu.only4.danmuku.application.queries._share.model.UserLoginLog
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginResult
import edu.only4.danmuku.domain.aggregates.user_login_log.enums.LoginType

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.and
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.ge
import org.babyfish.jimmer.sql.kt.ast.expression.le

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
        val now = request.now ?: System.currentTimeMillis() / 1000L
        val start = now - request.windowSeconds

        val count = sqlClient.createQuery(UserLoginLog::class) {
            where(
                and(
                    table.loginType eq LoginType.PASSWORD,
                    table.result eq LoginResult.FAILURE,
                    table.occurTime ge start,
                    table.occurTime le now,
                    request.userId?.let { table.userId eq it } ?: (table.loginName eq request.loginName)
                )
            )
            select(count(table.id))
        }.fetchOne()

        return GetRecentPasswordFailureCountQry.Response(failureCount = count)
    }
}
