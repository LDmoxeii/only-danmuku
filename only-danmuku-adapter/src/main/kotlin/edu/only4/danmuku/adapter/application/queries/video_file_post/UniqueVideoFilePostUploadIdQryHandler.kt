package edu.only4.danmuku.adapter.application.queries.video_file_post

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries._share.model.VideoFilePost
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.uploadId
import edu.only4.danmuku.application.queries.video_file_post.UniqueVideoFilePostUploadIdQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.`ne?`
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.exists

import org.springframework.stereotype.Service

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
@Service
class UniqueVideoFilePostUploadIdQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueVideoFilePostUploadIdQry.Request, UniqueVideoFilePostUploadIdQry.Response> {

    override fun exec(request: UniqueVideoFilePostUploadIdQry.Request): UniqueVideoFilePostUploadIdQry.Response {
        val exists = sqlClient.exists(VideoFilePost::class) {
            where(table.uploadId eq request.uploadId)
            where(table.customerId eq request.customerId)
            where(table.id `ne?` request.excludeVideoFilePostId)
        }

        return UniqueVideoFilePostUploadIdQry.Response(
            exists = exists
        )
    }
}

