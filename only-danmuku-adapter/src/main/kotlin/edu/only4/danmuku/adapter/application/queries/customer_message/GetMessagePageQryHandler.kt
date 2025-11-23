package edu.only4.danmuku.adapter.application.queries.customer_message

import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.model.CustomerMessage
import edu.only4.danmuku.application.queries._share.model.CustomerProfile
import edu.only4.danmuku.application.queries._share.model.Video
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.messageType
import edu.only4.danmuku.application.queries._share.model.userId
import edu.only4.danmuku.application.queries.message.GetMessagePageQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
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
                when(request.messageType) {
                    4 -> where(table.messageType.valueIn(listOf(4, 5)))
                    else -> where(table.messageType eq request.messageType)
                }
            }
            orderBy(table.id.desc())
            select(table)
        }.fetchPage(request.pageNum - 1, request.pageSize)

        // 预取发送者与视频信息
        val sendUserIds = page.rows.mapNotNull { it.sendSubjectId }.toSet()
        val videoIds = page.rows.mapNotNull { it.videoId }.toSet()

        val profileMap: Map<Long, CustomerProfile> = if (sendUserIds.isNotEmpty()) {
            sqlClient.createQuery(CustomerProfile::class) {
                where(table.userId valueIn sendUserIds)
                select(table)
            }.execute().associateBy { it.userId }
        } else emptyMap()

        val videoMap: Map<Long, Video> = if (videoIds.isNotEmpty()) {
            sqlClient.createQuery(Video::class) {
                where(table.id valueIn videoIds)
                select(table)
            }.execute().associateBy { it.id }
        } else emptyMap()

        val list = page.rows.map { row ->
            GetMessagePageQry.Response(
                id = row.id,
                messageType = row.messageType,
                readType = row.readType,
                extendJson = row.extendJson,
                createTime = row.createTime ?: 0L,
                // 扩展显示字段
                videoId = row.videoId,
                videoName = row.videoId?.let { vid -> videoMap[vid]?.videoName },
                videoCover = row.videoId?.let { vid -> videoMap[vid]?.videoCover },
                sendUserId = row.sendSubjectId,
                sendUserName = row.sendSubjectId?.let { sid -> profileMap[sid]?.nickName },
                sendUserAvatar = row.sendSubjectId?.let { sid -> profileMap[sid]?.avatar },
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
