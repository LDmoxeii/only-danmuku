package edu.only4.danmuku.adapter.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.query.ListQuery

import edu.only4.danmuku.application.queries.video_encrypt.ListVideoQualityAuthQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient

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

        return listOf(ListVideoQualityAuthQry.Response(
            policiesJson = TODO("set policiesJson")
        ))

    }
}
