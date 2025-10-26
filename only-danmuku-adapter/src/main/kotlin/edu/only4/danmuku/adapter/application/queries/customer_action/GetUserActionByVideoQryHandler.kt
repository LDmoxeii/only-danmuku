package edu.only4.danmuku.adapter.application.queries.customer_action

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.customer_action.GetUserActionByVideoQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.springframework.stereotype.Service

/**
 * 查询用户对视频/评论的已有行为
 */
@Service
class GetUserActionByVideoQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetUserActionByVideoQry.Request, GetUserActionByVideoQry.Response> {

    override fun exec(request: GetUserActionByVideoQry.Request): GetUserActionByVideoQry.Response {
        val actions = sqlClient.createQuery(CustomerAction::class) {
            where(table.customerId eq request.customerId)
            where(table.videoId eq request.videoId)
            where(table.commentId `eq?` request.commentId)
            where(table.actionType `eq?` request.actionType)
            select(table)
        }.execute()

        val action = actions.firstOrNull()

        return GetUserActionByVideoQry.Response(
            hasAction = action != null,
            action = action?.let {
                GetUserActionByVideoQry.ActionDetail(
                    id = it.id,
                    actionType = it.actionType,
                    actionCount = it.actionCount,
                    actionTime = it.actionTime
                )
            }
        )
    }
}
