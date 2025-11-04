package edu.only4.danmuku.adapter.application.queries.customer_action

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries._share.model.CustomerAction
import edu.only4.danmuku.application.queries._share.model.actionType
import edu.only4.danmuku.application.queries._share.model.commentId
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.videoId
import edu.only4.danmuku.application.queries.customer_action.UniqueCustomerActionTypeQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.`ne?`
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.exists

import org.springframework.stereotype.Service

/**
 * 用户行为 点赞、评论;
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/04
 */
@Service
class UniqueCustomerActionTypeQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueCustomerActionTypeQry.Request, UniqueCustomerActionTypeQry.Response> {

    override fun exec(request: UniqueCustomerActionTypeQry.Request): UniqueCustomerActionTypeQry.Response {
        val exists = sqlClient.exists(CustomerAction::class) {
            where(table.videoId eq request.videoId)
            where(table.commentId eq request.commentId)
            where(table.actionType eq request.actionType)
            where(table.customerId eq request.customerId)
            where(table.id `ne?` request.excludeCustomerActionId)
        }

        return UniqueCustomerActionTypeQry.Response(
            exists = exists
        )
    }
}

