package edu.only4.danmuku.adapter.application.queries.customer_message

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.CustomerMessage
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.messageType
import edu.only4.danmuku.application.queries._share.model.readType
import edu.only4.danmuku.application.queries.message.GetNoReadMessageCountGroupQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/** 获取未读消息数(分组)（Jimmer SQL） */
@Service
class GetNoReadMessageCountGroupQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetNoReadMessageCountGroupQry.Request, GetNoReadMessageCountGroupQry.Response> {

    override fun exec(request: GetNoReadMessageCountGroupQry.Request): GetNoReadMessageCountGroupQry.Response {
        val currentUserId = LoginHelper.getUserId() ?: return GetNoReadMessageCountGroupQry.Response()

        val types: List<Int> = sqlClient.createQuery(CustomerMessage::class) {
            where(table.customerId eq currentUserId.toString())
            // 1 = UNREAD
            where(table.readType eq 1)
            select(table.messageType)
        }.execute()

        val grouped = types.groupingBy { it }.eachCount()
        val list = grouped.entries.map { (type, cnt) ->
            GetNoReadMessageCountGroupQry.Response.Item(messageType = type, count = cnt)
        }
        return GetNoReadMessageCountGroupQry.Response(list = list)
    }
}
