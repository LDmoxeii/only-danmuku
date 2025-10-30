package edu.only4.danmuku.adapter.application.queries.customer_message

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.model.CustomerMessage
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.messageType
import edu.only4.danmuku.application.queries.message.GetMessagePageQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/** 获取消息分页（Jimmer SQL） */
@Service
class GetMessagePageQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetMessagePageQry.Request, GetMessagePageQry.Response> {

    override fun exec(request: GetMessagePageQry.Request): PageData<GetMessagePageQry.Response> {
        val currentUserId = LoginHelper.getUserId()
            ?: return PageData.empty(request.pageSize, request.pageNum)

        val page = sqlClient.createQuery(CustomerMessage::class) {
            where(table.customerId eq currentUserId)
            if (request.messageType != null) {
                where(table.messageType eq request.messageType!!)
            }
            orderBy(table.id.desc())
            select(table)
        }.fetchPage(request.pageNum - 1, request.pageSize)

        val list = page.rows.map { row ->
            GetMessagePageQry.Response(
                id = row.id,
                messageType = row.messageType,
                readType = row.readType,
                extendJson = row.extendJson,
                createTime = row.createTime ?: 0L,
            )
        }
        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            totalCount = page.totalRowCount,
            list = list,
        )
    }
}
