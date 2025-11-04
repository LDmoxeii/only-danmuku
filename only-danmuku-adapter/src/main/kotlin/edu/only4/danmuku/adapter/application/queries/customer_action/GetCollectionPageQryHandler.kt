package edu.only4.danmuku.adapter.application.queries.customer_action

import com.only4.cap4k.ddd.core.application.query.PageQuery
import com.only4.cap4k.ddd.core.share.PageData
import edu.only4.danmuku.application.queries._share.model.CustomerAction
import edu.only4.danmuku.application.queries._share.model.actionType
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.dto.CustomerAction.CustomerActionSimple
import edu.only4.danmuku.application.queries.customer_action.GetCollectionPageQry
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 用户收藏的视频ID列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetCollectionPageQryHandler(
    private val sqlClient: KSqlClient,
) : PageQuery<GetCollectionPageQry.Request, GetCollectionPageQry.Response> {

    override fun exec(request: GetCollectionPageQry.Request): PageData<GetCollectionPageQry.Response> {

        val pageResult =
            sqlClient.createQuery(CustomerAction::class) {
                where(table.customerId eq request.customerId)
                where(table.actionType eq ActionType.FAVORITE_VIDEO)
                select(table.fetch(CustomerActionSimple::class))
            }.fetchPage(request.pageNum - 1, request.pageSize)

        return PageData.create(
            pageNum = request.pageNum,
            pageSize = request.pageSize,
            list = pageResult.rows.map { action ->
                GetCollectionPageQry.Response(
                    actionId = action.id,
                    videoId = action.video?.id,
                    videoUserId = action.videoOwnerId,
                    commentId = action.commentId,
                    actionType = action.actionType,
                    actionCount = action.actionCount,
                    userId = action.customerId,
                    actionTime = action.actionTime,
                    videoName = action.video?.videoName,
                    videoCover = action.video?.videoCover,
                )
            },
            totalCount = pageResult.totalRowCount
        )
    }
}
