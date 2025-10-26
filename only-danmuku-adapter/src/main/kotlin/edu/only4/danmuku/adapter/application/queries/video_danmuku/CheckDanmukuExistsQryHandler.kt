package edu.only4.danmuku.adapter.application.queries.video_danmuku

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoDanmuku
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries.video_danmuku.CheckDanmukuExistsQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.exists
import org.springframework.stereotype.Service

@Service
class CheckDanmukuExistsQryHandler(
    private val sqlClient: KSqlClient,
) : Query<CheckDanmukuExistsQry.Request, CheckDanmukuExistsQry.Response> {
    override fun exec(request: CheckDanmukuExistsQry.Request): CheckDanmukuExistsQry.Response {
        val exists = sqlClient.exists(VideoDanmuku::class) {
            where(table.id eq request.danmukuId)
        }
        return CheckDanmukuExistsQry.Response(exists = exists)
    }
}

