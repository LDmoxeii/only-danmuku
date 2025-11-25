package edu.only4.danmuku.adapter.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_encrypt.GetLatestVideoHlsKeyQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient

/**
 * 查询可用的最新密钥元数据（不返回明文），用于拼接 m3u8 key URI
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class GetLatestVideoHlsKeyQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetLatestVideoHlsKeyQry.Request, GetLatestVideoHlsKeyQry.Response> {

    override fun exec(request: GetLatestVideoHlsKeyQry.Request): GetLatestVideoHlsKeyQry.Response {

        return GetLatestVideoHlsKeyQry.Response(
            keyId = TODO("set keyId"),
            keyVersion = TODO("set keyVersion"),
            keyQuality = TODO("set keyQuality"),
            keyUriTemplate = TODO("set keyUriTemplate"),
            status = TODO("set status")
        )
    }
}
