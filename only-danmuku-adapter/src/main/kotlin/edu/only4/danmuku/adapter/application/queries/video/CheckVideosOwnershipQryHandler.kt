package edu.only4.danmuku.adapter.application.queries.video

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.Video
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries.video.CheckVideosOwnershipQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.stereotype.Service

@Service
class CheckVideosOwnershipQryHandler(
    private val sqlClient: KSqlClient,
) : Query<CheckVideosOwnershipQry.Request, CheckVideosOwnershipQry.Response> {

    override fun exec(request: CheckVideosOwnershipQry.Request): CheckVideosOwnershipQry.Response {
        if (request.videoIds.isEmpty()) {
            return CheckVideosOwnershipQry.Response(allOwned = true, missing = emptyList())
        }

        val owned = sqlClient.createQuery(Video::class) {
            where(table.customerId eq request.userId)
            where(table.id valueIn request.videoIds)
            select(table.id)
        }.execute()

        val ownedSet = owned.toSet()
        val missing = request.videoIds.filterNot(ownedSet::contains)
        return CheckVideosOwnershipQry.Response(missing.isEmpty(), missing)
    }
}

