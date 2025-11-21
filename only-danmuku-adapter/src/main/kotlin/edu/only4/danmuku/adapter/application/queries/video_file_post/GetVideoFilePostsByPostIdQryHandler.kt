package edu.only4.danmuku.adapter.application.queries.video_file_post

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.video_file_post.GetVideoFilePostsByPostIdQry

import org.springframework.stereotype.Service
import org.babyfish.jimmer.sql.kt.KSqlClient

/**
 * 查询稿件下的所有 VideoFilePost（含转码状态）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Service
class GetVideoFilePostsByPostIdQryHandler(
    private val sqlClient: KSqlClient,
) : Query<GetVideoFilePostsByPostIdQry.Request, GetVideoFilePostsByPostIdQry.Response> {

    override fun exec(request: GetVideoFilePostsByPostIdQry.Request): GetVideoFilePostsByPostIdQry.Response {

        return GetVideoFilePostsByPostIdQry.Response(

        )
    }
}
