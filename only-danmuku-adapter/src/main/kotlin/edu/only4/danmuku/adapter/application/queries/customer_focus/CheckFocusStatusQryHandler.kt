package edu.only4.danmuku.adapter.application.queries.customer_focus

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.dto.CustomerFocus.CustomerFocusSimple
import edu.only4.danmuku.application.queries._share.model.focusCustomerId
import edu.only4.danmuku.application.queries.customer_focus.CheckFocusStatusQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 检查关注状态
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class CheckFocusStatusQryHandler(
    private val sqlClient: KSqlClient,
) : Query<CheckFocusStatusQry.Request, CheckFocusStatusQry.Response> {

    override fun exec(request: CheckFocusStatusQry.Request): CheckFocusStatusQry.Response {

        // 查询是否存在关注关系
        val focusRecords = sqlClient.findAll(CustomerFocusSimple::class) {
            where(table.customerId eq request.userId)
            where(table.focusCustomerId eq request.focusUserId)
        }

        return CheckFocusStatusQry.Response(
            haveFocus = focusRecords.isNotEmpty()
        )
    }
}
