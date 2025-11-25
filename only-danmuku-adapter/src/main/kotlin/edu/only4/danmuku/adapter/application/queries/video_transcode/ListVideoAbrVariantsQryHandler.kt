package edu.only4.danmuku.adapter.application.queries.video_transcode

import com.only4.cap4k.ddd.core.application.query.ListQuery

import edu.only4.danmuku.application.queries.video_transcode.ListVideoAbrVariantsQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient

/**
 * 查询指定 fileId 的可用清晰度档位列表（quality、宽高、码率、路径）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class ListVideoAbrVariantsQryHandler(
    private val sqlClient: KSqlClient,
) : ListQuery<ListVideoAbrVariantsQry.Request, ListVideoAbrVariantsQry.Response> {

    override fun exec(request: ListVideoAbrVariantsQry.Request): List<ListVideoAbrVariantsQry.Response> {

        return listOf(ListVideoAbrVariantsQry.Response(
            qualities = TODO("set qualities"),
            variantJson = TODO("set variantJson")
        ))

    }
}
