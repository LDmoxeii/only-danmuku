package edu.only4.danmuku.adapter.application.queries.video_file_upload_session

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.video_file_upload_session.GetUploadedTempPathsQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 获取上传完毕的临时路径
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/02
 */
@Service
class GetUploadedTempPathsQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<GetUploadedTempPathsQry.Request, String> {

    override fun exec(request: GetUploadedTempPathsQry.Request): List<String> {
        // 依据 videoId 和 customerId 关联查询上传会话的临时路径
        val tempPaths = sqlClient.createQuery(VideoFilePost::class) {
            where(table.videoPost.id eq request.videoId)
            where(table.videoPost.customerId eq request.customerId)
            select(table.upload.tempDir)
        }.execute()

        return tempPaths.filterNotNull().distinct()
    }
}
