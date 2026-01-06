package edu.only4.danmuku.adapter.application.queries.video_post_processing

import com.only4.cap4k.ddd.core.application.query.ListQuery

import edu.only4.danmuku.application.queries._share.model.VideoHlsEncryptKey
import edu.only4.danmuku.application.queries._share.model.VideoPostProcessingFile
import edu.only4.danmuku.application.queries._share.model.fileIndex
import edu.only4.danmuku.application.queries._share.model.transcodeOutputPrefix
import edu.only4.danmuku.application.queries._share.model.transcodeVariantsJson
import edu.only4.danmuku.application.queries._share.model.encryptOutputPrefix
import edu.only4.danmuku.application.queries._share.model.duration
import edu.only4.danmuku.application.queries._share.model.fileSize
import edu.only4.danmuku.application.queries._share.model.videoPostProcessing
import edu.only4.danmuku.application.queries._share.model.videoPostId
import edu.only4.danmuku.application.queries.video_post_processing.ListVideoPostProcessingFilesForSyncQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq

/**
 * 查询处理完成后的文件清单与产物摘要（含分辨率档位），用于回填稿件态文件
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@Service
class ListVideoPostProcessingFilesForSyncQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<ListVideoPostProcessingFilesForSyncQry.Request, ListVideoPostProcessingFilesForSyncQry.Response> {

    override fun exec(request: ListVideoPostProcessingFilesForSyncQry.Request): List<ListVideoPostProcessingFilesForSyncQry.Response> {

        val files = sqlClient.createQuery(VideoPostProcessingFile::class) {
            where(table.videoPostProcessing.videoPostId eq request.videoPostId)
            select(
                table.fetchBy {
                    fileIndex()
                    transcodeOutputPrefix()
                    encryptOutputPrefix()
                    transcodeVariantsJson()
                    duration()
                    fileSize()
                }
            )
        }.execute()

        val keys = sqlClient.createQuery(VideoHlsEncryptKey::class) {
            where(table.videoPostId eq request.videoPostId)
            select(table)
        }.execute()
        val keyMap = keys.groupBy { it.fileIndex }.mapValues { entry ->
            entry.value.maxByOrNull { it.keyVersion }
        }

        return files.map { file ->
            val key = keyMap[file.fileIndex]
            ListVideoPostProcessingFilesForSyncQry.Response(
                fileIndex = file.fileIndex,
                transcodeOutputPrefix = file.transcodeOutputPrefix,
                encryptOutputPrefix = file.encryptOutputPrefix,
                variantsJson = file.transcodeVariantsJson,
                duration = file.duration,
                fileSize = file.fileSize,
                encryptMethod = key?.method?.name,
                keyVersion = key?.keyVersion
            )
        }

    }
}
