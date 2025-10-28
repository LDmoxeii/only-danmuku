package edu.only4.danmuku.adapter.application.queries.file_upload_session

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoFileUploadSession
import edu.only4.danmuku.application.queries._share.model.customerId
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries.file_upload_session.GetUploadSessionByIdQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 根据会话ID获取上传会话信息
 */
@Service
class GetUploadSessionByIdQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetUploadSessionByIdQry.Request, GetUploadSessionByIdQry.Response> {

    override fun exec(request: GetUploadSessionByIdQry.Request): GetUploadSessionByIdQry.Response {
        val session = sqlClient.createQuery(VideoFileUploadSession::class) {
            where(table.id eq request.uploadId)
            where(table.customerId eq request.userId)
            select(table)
        }.fetchOne()

        return GetUploadSessionByIdQry.Response(
            exists = true,
            id = session.id,
            customerId = session.customerId,
            fileName = session.fileName,
            chunks = session.chunks,
            chunkIndex = session.chunkIndex,
            fileSize = session.fileSize,
            tempPath = session.tempPath,
            status = session.status,
            createTime = session.createTime,
            updateTime = session.updateTime,
            expiresAt = session.expiresAt,
        )
    }
}

