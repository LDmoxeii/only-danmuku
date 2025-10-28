package edu.only4.danmuku.adapter.application.queries.customer_action

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.model.CustomerAction
import edu.only4.danmuku.application.queries._share.model.fetchBy
import edu.only4.danmuku.application.queries.customer_action.GetUserActionsByVideoIdQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取指定用户在指定视频下的行为列表
 */
@Service
class GetUserActionsByVideoIdQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<GetUserActionsByVideoIdQry.Request, GetUserActionsByVideoIdQry.Response> {

    override fun exec(request: GetUserActionsByVideoIdQry.Request): List<GetUserActionsByVideoIdQry.Response> {
        val actions = sqlClient.createQuery(CustomerAction::class) {
            where(table.customer.id eq request.userId)
            where(table.video.id eq request.videoId)
            select(
                table.fetchBy {
                    allScalarFields()
                    customer { id() }
                    videoOwner { id() }
                    video {
                        id()
                        videoName()
                        videoCover()
                    }
                    comment { id() }
                }
            )
        }.execute()

        return actions.map { it ->
            GetUserActionsByVideoIdQry.Response(
                actionId = it.id,
                userId = it.customer.id,
                videoId = it.video.id,
                videoName = it.video.videoName,
                videoCover = it.video.videoCover,
                videoUserId = it.videoOwner.id,
                commentId = it.comment?.id,
                actionType = it.actionType,
                actionCount = it.actionCount,
                actionTime = it.actionTime,
            )
        }
    }
}

