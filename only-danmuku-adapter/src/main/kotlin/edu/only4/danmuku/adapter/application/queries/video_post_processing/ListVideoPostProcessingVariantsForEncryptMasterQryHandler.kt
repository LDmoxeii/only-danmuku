package edu.only4.danmuku.adapter.application.queries.video_post_processing

import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.model.VideoPostProcessingVariant
import edu.only4.danmuku.application.queries._share.model.bandwidthBps
import edu.only4.danmuku.application.queries._share.model.encryptStatus
import edu.only4.danmuku.application.queries._share.model.fileIndex
import edu.only4.danmuku.application.queries._share.model.height
import edu.only4.danmuku.application.queries._share.model.playlistPath
import edu.only4.danmuku.application.queries._share.model.quality
import edu.only4.danmuku.application.queries._share.model.videoPostId
import edu.only4.danmuku.application.queries._share.model.videoPostProcessing
import edu.only4.danmuku.application.queries._share.model.videoPostProcessingFile
import edu.only4.danmuku.application.queries._share.model.width
import edu.only4.danmuku.application.queries.video_post_processing.ListVideoPostProcessingVariantsForEncryptMasterQry
import edu.only4.danmuku.domain.aggregates.video_post_processing.enums.ProcessStatus
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 查询某分P的清晰度档位元数据，用于生成加密 master
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
@Service
class ListVideoPostProcessingVariantsForEncryptMasterQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<ListVideoPostProcessingVariantsForEncryptMasterQry.Request, ListVideoPostProcessingVariantsForEncryptMasterQry.Response> {

    override fun exec(request: ListVideoPostProcessingVariantsForEncryptMasterQry.Request): List<ListVideoPostProcessingVariantsForEncryptMasterQry.Response> {
        val variants = sqlClient.createQuery(VideoPostProcessingVariant::class) {
            where(table.videoPostProcessingFile.videoPostProcessing.videoPostId eq request.videoPostId)
            where(table.videoPostProcessingFile.fileIndex eq request.fileIndex)
            where(table.encryptStatus eq ProcessStatus.SUCCESS)
            select(
                table.fetchBy {
                    quality()
                    width()
                    height()
                    bandwidthBps()
                    playlistPath()
                }
            )
        }.execute()

        return variants.sortedWith(
            compareByDescending<VideoPostProcessingVariant> { it.bandwidthBps }
                .thenBy { it.quality }
        ).map { variant ->
            ListVideoPostProcessingVariantsForEncryptMasterQry.Response(
                quality = variant.quality,
                width = variant.width,
                height = variant.height,
                bandwidthBps = variant.bandwidthBps,
                playlistPath = variant.playlistPath
            )
        }

    }
}
