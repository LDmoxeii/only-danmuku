package edu.only4.danmuku.adapter.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoPost
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.status
import edu.only4.danmuku.application.queries.video_draft.GetVideoDraftCountByStatusQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
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
        val drafts = sqlClient.createQuery(VideoPost::class) {
            where(table.customerId eq request.userId)
            where(table.status `eq?` request.status)

            // 如果有排除状态数组，则排除这些状态
            where(table.status `valueNotIn?` request.excludeStatusArray)
            select(count(table))
        }.fetchOne()

        return GetVideoDraftCountByStatusQry.Response(
            count = drafts
        )
    }
}
