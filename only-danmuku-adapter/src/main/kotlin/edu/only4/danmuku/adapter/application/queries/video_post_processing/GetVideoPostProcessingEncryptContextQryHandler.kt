package edu.only4.danmuku.adapter.application.queries.video_post_processing

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.video_post_processing.GetVideoPostProcessingEncryptContextQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 查询处理文件加密上下文（输出前缀/目录）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
@Service
class GetVideoPostProcessingEncryptContextQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetVideoPostProcessingEncryptContextQry.Request, GetVideoPostProcessingEncryptContextQry.Response> {

    override fun exec(request: GetVideoPostProcessingEncryptContextQry.Request): GetVideoPostProcessingEncryptContextQry.Response {
        val file = sqlClient.createQuery(VideoPostProcessingFile::class) {
            where(table.parent.videoPostId eq request.videoPostId)
            where(table.fileIndex eq request.fileIndex)
            select(
                table.fetchBy {
                    transcodeOutputPrefix()
                    encryptOutputDir()
                }
            )
        }.fetchOneOrNull()

        return GetVideoPostProcessingEncryptContextQry.Response(
            transcodeOutputPrefix = file?.transcodeOutputPrefix,
            encryptOutputDir = file?.encryptOutputDir
        )
    }
}
