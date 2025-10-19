package edu.only4.danmuku.adapter.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.draft.video_draft.VideoDraftCount
import edu.only4.danmuku.application.queries._share.model.video_draft.customerId
import edu.only4.danmuku.application.queries._share.model.video_draft.status
import edu.only4.danmuku.application.queries.video_draft.GetVideoDraftCountByStatusQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.babyfish.jimmer.sql.kt.ast.expression.`valueNotIn?`
import org.springframework.stereotype.Service

/**
 * 按状态统计视频草稿数量
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class GetVideoDraftCountByStatusQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetVideoDraftCountByStatusQry.Request, GetVideoDraftCountByStatusQry.Response> {

    override fun exec(request: GetVideoDraftCountByStatusQry.Request): GetVideoDraftCountByStatusQry.Response {

        // 查询满足条件的视频草稿
        val drafts = sqlClient.findAll(VideoDraftCount::class) {
            where(table.customerId eq request.userId)
            where(table.status `eq?` request.status?.toByte())

            // 如果有排除状态数组，则排除这些状态
            where(table.status `valueNotIn?` request.excludeStatusArray?.map { it.toByte() })
        }

        return GetVideoDraftCountByStatusQry.Response(
            count = drafts.size
        )
    }
}
