package edu.only4.danmuku.adapter.application.queries.message

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.CustomerMessage
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.readType
import edu.only4.danmuku.application.queries.message.GetNoReadMessageCountQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/** 获取未读消息数（Jimmer SQL） */
@Service
class GetNoReadMessageCountQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetNoReadMessageCountQry.Request, GetNoReadMessageCountQry.Response> {

    override fun exec(request: GetNoReadMessageCountQry.Request): GetNoReadMessageCountQry.Response {
        val currentUserId = LoginHelper.getUserId() ?: return GetNoReadMessageCountQry.Response()

        val unreadCount = sqlClient.createQuery(CustomerMessage::class) {
            where(table.customerId eq currentUserId.toString())
            // 1 = UNREAD
            where(table.readType eq 1)
            select(count(table))
        }.fetchOne()

        return GetNoReadMessageCountQry.Response(count = unreadCount)
    }
}
