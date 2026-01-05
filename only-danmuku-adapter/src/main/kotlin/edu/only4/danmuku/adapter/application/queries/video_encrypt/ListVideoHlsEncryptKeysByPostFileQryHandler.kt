package edu.only4.danmuku.adapter.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.query.ListQuery

import edu.only4.danmuku.application.queries.video_encrypt.ListVideoHlsEncryptKeysByPostFileQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient

/**
 * 按 videoPostId + fileIndex 查询密钥ID列表（转正绑定用）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@Service
class ListVideoHlsEncryptKeysByPostFileQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<ListVideoHlsEncryptKeysByPostFileQry.Request, ListVideoHlsEncryptKeysByPostFileQry.Response> {

    override fun exec(request: ListVideoHlsEncryptKeysByPostFileQry.Request): List<ListVideoHlsEncryptKeysByPostFileQry.Response> {

        return listOf(ListVideoHlsEncryptKeysByPostFileQry.Response(
            encryptKeyId = TODO("set encryptKeyId")
        ))

    }
}
