package edu.only4.danmuku.adapter.application.queries.video_danmuku

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.video_danmuku.JVideoDanmuku
import edu.only4.danmuku.application.queries.video_danmuku.CheckDanmukuExistsQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.exists
import org.springframework.stereotype.Service
import edu.only4.danmuku.application.queries._share.model.video_danmuku.id as danmukuId

@Service
class CheckDanmukuExistsQryHandler(
    private val sqlClient: KSqlClient,
) : Query<CheckDanmukuExistsQry.Request, CheckDanmukuExistsQry.Response> {
    override fun exec(request: CheckDanmukuExistsQry.Request): CheckDanmukuExistsQry.Response {
        val exists = sqlClient.exists(JVideoDanmuku::class) {
            where(table.danmukuId eq request.danmukuId)
        }
        return CheckDanmukuExistsQry.Response(exists = exists)
    }
}

