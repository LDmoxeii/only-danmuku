package edu.only4.danmuku.adapter.application.queries.video_transcode

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_transcode.GetVideoAbrMasterQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient

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

        return GetVideoAbrMasterQry.Response(
            status = TODO("set status")
        )
    }
}
