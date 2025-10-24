package edu.only4.danmuku.adapter.application.queries.video_danmuku

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.video_danmuku.JVideoDanmuku
import edu.only4.danmuku.application.queries.video_danmuku.GetDanmukuOwnerQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service
import edu.only4.danmuku.application.queries._share.model.video_danmuku.id as danmukuId

@Service
class GetDanmukuOwnerQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetDanmukuOwnerQry.Request, GetDanmukuOwnerQry.Response> {
    override fun exec(request: GetDanmukuOwnerQry.Request): GetDanmukuOwnerQry.Response {
        val list = sqlClient.createQuery(JVideoDanmuku::class) {
            where(table.danmukuId eq request.danmukuId)
            select(table)
        }.execute()

        val item = list.firstOrNull() ?: throw IllegalArgumentException("弹幕不存在: id=${request.danmukuId}")
        return GetDanmukuOwnerQry.Response(
            videoId = item.videoId,
            ownerId = item.video.customerId
        )
    }
}

