package edu.only4.danmuku.adapter.application.queries.video_post_processing

import com.only4.cap4k.ddd.core.application.query.ListQuery

import edu.only4.danmuku.application.queries.video_post_processing.ListVideoPostProcessingFilesForSyncQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient

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

        return listOf(ListVideoPostProcessingFilesForSyncQry.Response(
            fileIndex = TODO("set fileIndex"),
            transcodeOutputPrefix = TODO("set transcodeOutputPrefix"),
            encryptOutputPrefix = TODO("set encryptOutputPrefix"),
            variantsJson = TODO("set variantsJson"),
            duration = TODO("set duration"),
            fileSize = TODO("set fileSize"),
            encryptMethod = TODO("set encryptMethod"),
            keyVersion = TODO("set keyVersion")
        ))

    }
}
