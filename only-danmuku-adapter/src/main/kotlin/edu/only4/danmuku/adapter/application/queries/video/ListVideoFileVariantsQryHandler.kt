package edu.only4.danmuku.adapter.application.queries.video

import com.only.engine.json.misc.JsonUtils
import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.model.VideoFileVariant
import edu.only4.danmuku.application.queries._share.model.videoFileId
import edu.only4.danmuku.application.queries.video.ListVideoFileVariantsQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

@Service
class ListVideoFileVariantsQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<ListVideoFileVariantsQry.Request, ListVideoFileVariantsQry.Response> {

    override fun exec(request: ListVideoFileVariantsQry.Request): List<ListVideoFileVariantsQry.Response> {
        val variants = sqlClient.createQuery(VideoFileVariant::class) {
            where(table.videoFileId eq request.fileId)
            select(table)
        }.execute()

        if (variants.isEmpty()) return emptyList()

        val sortedVariants = variants.sortedWith(
            compareByDescending<VideoFileVariant> { qualityScore(it.quality) }
                .thenBy { it.quality }
        )

        val qualities = sortedVariants.map { it.quality }
        val variantJson = JsonUtils.toJsonString(sortedVariants) ?: "[]"

        return listOf(
            ListVideoFileVariantsQry.Response(
                qualities = qualities,
                variantJson = variantJson
            )
        )
    }

    private fun qualityScore(quality: String): Int {
        val number = QUALITY_NUMBER_REGEX.find(quality)?.groupValues?.getOrNull(1)?.toIntOrNull()
        return number ?: Int.MIN_VALUE
    }

    companion object {
        private val QUALITY_NUMBER_REGEX = Regex("(\\d+)")
    }
}
