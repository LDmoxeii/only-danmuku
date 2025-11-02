package edu.only4.danmuku.application.distributed.clients

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 从硬盘物理删除视频文件资源
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/02
 */
object DeleteVideoFileResourcesCli {

    class Request(
        val videoId: Long,
        val ownerId: Long,
    ) : RequestParam<Unit>
}
