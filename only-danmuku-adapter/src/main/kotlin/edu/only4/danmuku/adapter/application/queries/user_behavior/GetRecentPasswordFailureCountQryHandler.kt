package edu.only4.danmuku.adapter.application.queries.user_behavior

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.user_behavior.GetRecentPasswordFailureCountQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient

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

        return GetRecentPasswordFailureCountQry.Response(

        )
    }
}
