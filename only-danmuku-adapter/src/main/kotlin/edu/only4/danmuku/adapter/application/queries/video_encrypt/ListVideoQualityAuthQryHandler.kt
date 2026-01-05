package edu.only4.danmuku.adapter.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.query.ListQuery
import com.only.engine.json.misc.JsonUtils
import edu.only4.danmuku.application.queries._share.model.VideoFile
import edu.only4.danmuku.application.queries._share.model.VideoQualityPolicy
import edu.only4.danmuku.application.queries._share.model.authPolicy
import edu.only4.danmuku.application.queries._share.model.fileIndex
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.quality
import edu.only4.danmuku.application.queries._share.model.video
import edu.only4.danmuku.application.queries._share.model.videoId
import edu.only4.danmuku.application.queries.video_encrypt.ListVideoQualityAuthQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 查询清晰度授权策略列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class ListVideoQualityAuthQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<ListVideoQualityAuthQry.Request, ListVideoQualityAuthQry.Response> {

    override fun exec(request: ListVideoQualityAuthQry.Request): List<ListVideoQualityAuthQry.Response> {
        val target = resolveTarget(request) ?: return emptyList()
        val (videoId, fileIndex) = target

        val policies = sqlClient.createQuery(VideoQualityPolicy::class) {
            where(table.videoId eq videoId)
            where(table.fileIndex eq fileIndex)
            select(table.quality, table.authPolicy)
        }.execute()

        val json = JsonUtils.toJsonString(
            policies.map { mapOf("quality" to it._1, "authPolicy" to it._2.code) }
        ) ?: "[]"

        return listOf(
            ListVideoQualityAuthQry.Response(
                policiesJson = json
            )
        )

    }

    private fun resolveTarget(request: ListVideoQualityAuthQry.Request): Pair<Long, Int>? {
        val videoFileId = request.videoFileId ?: return null
        return sqlClient.createQuery(VideoFile::class) {
            where(table.id eq videoFileId)
            select(table.video.id, table.fileIndex)
        }.fetchOneOrNull()?.let { it._1 to it._2 }
    }
}
