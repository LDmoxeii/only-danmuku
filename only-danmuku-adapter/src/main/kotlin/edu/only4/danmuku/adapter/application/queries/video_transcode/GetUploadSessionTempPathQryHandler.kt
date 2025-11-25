package edu.only4.danmuku.adapter.application.queries.video_transcode

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoFileUploadSession
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.tempPath
import edu.only4.danmuku.application.queries.video_transcode.GetUploadSessionTempPathQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 查询上传会话的临时目录 tempPath
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class GetUploadSessionTempPathQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetUploadSessionTempPathQry.Request, GetUploadSessionTempPathQry.Response> {

    override fun exec(request: GetUploadSessionTempPathQry.Request): GetUploadSessionTempPathQry.Response {
        val tempPath = sqlClient.createQuery(VideoFileUploadSession::class) {
            where(table.id eq request.uploadId)
            select(table.tempPath)
        }.fetchOneOrNull() ?: throw KnownException("上传会话不存在: ${request.uploadId}")

        if (tempPath.isBlank()) {
            throw KnownException("上传会话临时目录缺失: ${request.uploadId}")
        }

        return GetUploadSessionTempPathQry.Response(tempPath = tempPath)
    }
}
