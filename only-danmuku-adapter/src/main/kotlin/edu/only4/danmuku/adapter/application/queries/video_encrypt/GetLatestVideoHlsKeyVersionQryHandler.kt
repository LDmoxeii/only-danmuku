package edu.only4.danmuku.adapter.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_encrypt.GetLatestVideoHlsKeyVersionQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient

/**
 * 查询稿件最新 keyVersion（按批次）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
@Service
class GetLatestVideoHlsKeyVersionQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetLatestVideoHlsKeyVersionQry.Request, GetLatestVideoHlsKeyVersionQry.Response> {

    override fun exec(request: GetLatestVideoHlsKeyVersionQry.Request): GetLatestVideoHlsKeyVersionQry.Response {

        return GetLatestVideoHlsKeyVersionQry.Response(
            keyVersion = TODO("set keyVersion"),
            qualities = TODO("set qualities")
        )
    }
}
