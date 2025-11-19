package edu.only4.danmuku.application.distributed.clients

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 删除视频搜索索引
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/02
 */
object RemoveVideoSearchIndexCli {

    data class Request(
        val videoId: Long,
    ) : RequestParam<Unit>
}

