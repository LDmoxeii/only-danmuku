package edu.only4.danmuku.adapter.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.query.ListQuery

import edu.only4.danmuku.application.queries.video_encrypt.ListVideoHlsEncryptKeysQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient

/**
 * 按 fileId + keyVersion 查询质量 key 列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
@Service
class ListVideoHlsEncryptKeysQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<ListVideoHlsEncryptKeysQry.Request, ListVideoHlsEncryptKeysQry.Response> {

    override fun exec(request: ListVideoHlsEncryptKeysQry.Request): List<ListVideoHlsEncryptKeysQry.Response> {

        return listOf(ListVideoHlsEncryptKeysQry.Response(
            keysJson = TODO("set keysJson")
        ))

    }
}
