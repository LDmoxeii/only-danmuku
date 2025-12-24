package edu.only4.danmuku.adapter.application.queries.video_storage

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_storage.GetVideoHlsResourceUrlQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient

/**
 * 获取 HLS 资源 URL（master/playlist/segment）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
@Service
class GetVideoHlsResourceUrlQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetVideoHlsResourceUrlQry.Request, GetVideoHlsResourceUrlQry.Response> {

    override fun exec(request: GetVideoHlsResourceUrlQry.Request): GetVideoHlsResourceUrlQry.Response {

        return GetVideoHlsResourceUrlQry.Response(
            url = TODO("set url"),
            contentType = TODO("set contentType")
        )
    }
}
