package edu.only4.danmuku.adapter.application.queries.video_transcode

import com.only.engine.json.misc.JsonUtils
import com.only4.cap4k.ddd.core.application.query.ListQuery
import edu.only4.danmuku.application.queries._share.model.VideoHlsAbrVariant
import edu.only4.danmuku.application.queries._share.model.fileId
import edu.only4.danmuku.application.queries.video_transcode.ListVideoAbrVariantsQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 查询指定 fileId 的可用清晰度档位列表（quality、宽高、码率、路径）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class ListVideoAbrVariantsQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<ListVideoAbrVariantsQry.Request, ListVideoAbrVariantsQry.Response> {

    override fun exec(request: ListVideoAbrVariantsQry.Request): List<ListVideoAbrVariantsQry.Response> {

        val variants = sqlClient.createQuery(VideoHlsAbrVariant::class) {
            where(table.fileId eq request.fileId)
            select(table)
        }.execute()

        if (variants.isEmpty()) return emptyList()

        val qualities = variants.map { it.quality }
        val variantJson = JsonUtils.toJsonString(variants) ?: "[]"

        return listOf(
            ListVideoAbrVariantsQry.Response(
                qualities = qualities,
                variantJson = variantJson
            )
        )

    }
}
