package edu.only4.danmuku.application.queries.video

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 通过视频稿件Id判断视频是否存在
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/29
 */
object VideoExistByVideoPostIdQry {

    class Request(
        val videoPostId: Long,
    ) : RequestParam<Response>

    class Response(
        val exist: Boolean,
        val videoId: Long?,
    )
}
