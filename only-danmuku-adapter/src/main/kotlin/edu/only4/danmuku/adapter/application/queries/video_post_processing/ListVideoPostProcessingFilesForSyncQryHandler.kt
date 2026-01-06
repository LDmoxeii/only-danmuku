package edu.only4.danmuku.adapter.application.queries.video_post_processing

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.model.*
import edu.only4.danmuku.application.queries.video_post_processing.ListVideoPostProcessingFilesForSyncQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

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
                    duration()
                    fileSize()
                }
            )
        }.execute()

        val variants = sqlClient.createQuery(VideoPostProcessingVariant::class) {
            where(table.videoPostProcessingFile.videoPostProcessing.videoPostId eq request.videoPostId)
            select(
                table.fetchBy {
                    quality()
                    width()
                    height()
                    videoBitrateKbps()
                    audioBitrateKbps()
                    bandwidthBps()
                    playlistPath()
                    segmentPrefix()
                    segmentDuration()
                    videoPostProcessingFile {
                        fileIndex()
                    }
                }
            )
        }.execute()
        val variantMap = variants.groupBy { it.videoPostProcessingFile.fileIndex }

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
                variants = variantMap[file.fileIndex]
                    ?.sortedWith(
                        compareByDescending<VideoPostProcessingVariant> { it.bandwidthBps }
                            .thenBy { it.quality }
                    )
                    ?.map { variant ->
                        ListVideoPostProcessingFilesForSyncQry.VariantItem(
                            quality = variant.quality,
                            width = variant.width,
                            height = variant.height,
                            videoBitrateKbps = variant.videoBitrateKbps,
                            audioBitrateKbps = variant.audioBitrateKbps,
                            bandwidthBps = variant.bandwidthBps,
                            playlistPath = variant.playlistPath,
                            segmentPrefix = variant.segmentPrefix,
                            segmentDuration = variant.segmentDuration
                        )
                    } ?: emptyList(),
                duration = file.duration,
                fileSize = file.fileSize,
                encryptMethod = key?.method?.name,
                keyVersion = key?.keyVersion
            )
        }

    }
}
