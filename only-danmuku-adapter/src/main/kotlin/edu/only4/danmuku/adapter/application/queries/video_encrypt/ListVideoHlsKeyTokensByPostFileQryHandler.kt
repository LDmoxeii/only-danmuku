package edu.only4.danmuku.adapter.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.query.ListQuery

import edu.only4.danmuku.application.queries._share.model.VideoHlsKeyToken
import edu.only4.danmuku.application.queries._share.model.fileIndex
import edu.only4.danmuku.application.queries._share.model.id
import edu.only4.danmuku.application.queries._share.model.videoPostId
import edu.only4.danmuku.application.queries.video_encrypt.ListVideoHlsKeyTokensByPostFileQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq

/**
 * 按 videoPostId + fileIndex 查询 token ID 列表（转正绑定用）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@Service
class ListVideoHlsKeyTokensByPostFileQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<ListVideoHlsKeyTokensByPostFileQry.Request, ListVideoHlsKeyTokensByPostFileQry.Response> {

    override fun exec(request: ListVideoHlsKeyTokensByPostFileQry.Request): List<ListVideoHlsKeyTokensByPostFileQry.Response> {

        val ids = sqlClient.createQuery(VideoHlsKeyToken::class) {
            where(table.videoPostId eq request.videoPostId)
            where(table.fileIndex eq request.fileIndex)
            select(table.id)
        }.execute()

        return ids.map { id ->
            ListVideoHlsKeyTokensByPostFileQry.Response(
                tokenId = id
            )
        }
    }
}
