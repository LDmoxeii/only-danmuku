package edu.only4.danmuku.adapter.application.queries.file_storage

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.file_storage.GetResourceAccessUrlQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient

/**
 * 根据资源 key 返回可访问 URL（OSS）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
@Service
class GetResourceAccessUrlQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetResourceAccessUrlQry.Request, GetResourceAccessUrlQry.Response> {

    override fun exec(request: GetResourceAccessUrlQry.Request): GetResourceAccessUrlQry.Response {

        return GetResourceAccessUrlQry.Response(
            url = TODO("set url")
        )
    }
}
