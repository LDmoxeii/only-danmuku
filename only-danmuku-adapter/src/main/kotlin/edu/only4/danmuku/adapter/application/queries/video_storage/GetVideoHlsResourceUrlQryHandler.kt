package edu.only4.danmuku.adapter.application.queries.video_storage

import com.only.engine.exception.KnownException
import com.only.engine.oss.enums.AccessPolicyType
import com.only.engine.oss.factory.OssFactory
import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoFile
import edu.only4.danmuku.application.queries._share.model.filePath
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries.video_storage.GetVideoHlsResourceUrlQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service
import java.time.Duration

/**
 * 获取 HLS 资源 URL（master/playlist/segment）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
@Service
class GetVideoHlsResourceUrlQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetVideoHlsResourceUrlQry.Request, GetVideoHlsResourceUrlQry.Response> {

    override fun exec(request: GetVideoHlsResourceUrlQry.Request): GetVideoHlsResourceUrlQry.Response {
        val relative = request.relativePath.trim().trimStart('/')
        if (relative.contains("..")) {
            throw KnownException.illegalArgument("relativePath")
        }
        val base = sqlClient.createQuery(VideoFile::class) {
            where(table.id eq request.videoFileId)
            select(table.filePath)
        }.fetchOneOrNull() ?: throw KnownException("文件不存在: ${request.videoFileId}")
        if (base.isBlank()) {
            throw KnownException("filePath 为空: ${request.videoFileId}")
        }
        val objectKey = base.trimEnd('/') + "/" + relative
        val client = OssFactory.instance()
        val url = if (client.getAccessPolicy() == AccessPolicyType.PUBLIC) {
            client.publicUrlForKey(objectKey)
        } else {
            client.createPresignedGetUrl(objectKey, Duration.ofMinutes(10))
        }
        return GetVideoHlsResourceUrlQry.Response(
            url = url,
            contentType = resolveContentType(relative)
        )
    }

    private fun resolveContentType(path: String): String? {
        val lower = path.lowercase()
        return when {
            lower.endsWith(".m3u8") -> "application/vnd.apple.mpegurl"
            lower.endsWith(".ts") || lower.endsWith(".ts.enc") -> "video/mp2t"
            else -> null
        }
    }
}
