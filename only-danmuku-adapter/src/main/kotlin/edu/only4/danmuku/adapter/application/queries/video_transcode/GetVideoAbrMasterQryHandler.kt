package edu.only4.danmuku.adapter.application.queries.video_transcode

import com.only4.cap4k.ddd.core.application.query.Query
import edu.only4.danmuku.application.queries._share.model.VideoFilePost
import edu.only4.danmuku.application.queries._share.model.filePath
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.transcodeOutputPrefix
import edu.only4.danmuku.application.queries._share.model.transferResult
import edu.only4.danmuku.application.queries.video_transcode.GetVideoAbrMasterQry
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

/**
 * 查询指定 fileId 的 ABR 状态（master.m3u8 路径可由 file_path 衍生）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class GetVideoAbrMasterQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetVideoAbrMasterQry.Request, GetVideoAbrMasterQry.Response> {

    override fun exec(request: GetVideoAbrMasterQry.Request): GetVideoAbrMasterQry.Response {
        val row = sqlClient.createQuery(VideoFilePost::class) {
            where(table.id eq request.fileId)
            select(table.transferResult, table.transcodeOutputPrefix, table.filePath)
        }.fetchOneOrNull()

        val status = row?._1?.name ?: "UNKNOW"
        val outputPrefix = row?._2?.takeIf { it.isNotBlank() } ?: row?._3
        val masterPath = outputPrefix?.trimEnd('/')?.let { "$it/master.m3u8" }
        return GetVideoAbrMasterQry.Response(status = status, masterPath = masterPath)
    }
}
