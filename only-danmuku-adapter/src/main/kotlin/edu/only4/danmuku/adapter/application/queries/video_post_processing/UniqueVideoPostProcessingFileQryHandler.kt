package edu.only4.danmuku.adapter.application.queries.video_post_processing

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoPostProcessingFile
import edu.only4.danmuku.application.queries._share.model.fileIndex
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.parentId
import edu.only4.danmuku.application.queries.video_post_processing.UniqueVideoPostProcessingFileQry
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
class UniqueVideoPostProcessingFileQryHandler(
    private val sqlClient: KSqlClient,
) : Query<UniqueVideoPostProcessingFileQry.Request, UniqueVideoPostProcessingFileQry.Response> {

    override fun exec(request: UniqueVideoPostProcessingFileQry.Request): UniqueVideoPostProcessingFileQry.Response {
        val exists = sqlClient.exists(VideoPostProcessingFile::class) {
            where(table.parentId eq request.parentId)
            where(table.fileIndex eq request.fileIndex)
            where(table.id `ne?` request.excludeVideoPostProcessingFileId)
        }

        return UniqueVideoPostProcessingFileQry.Response(
            exists = exists
        )
    }
}
