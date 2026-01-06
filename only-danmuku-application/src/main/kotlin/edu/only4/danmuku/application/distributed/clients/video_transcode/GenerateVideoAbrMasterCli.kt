package edu.only4.danmuku.application.distributed.clients.video_transcode

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 防腐层：根据档位清单生成 master.m3u8 并上传到 outputPrefix
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
object GenerateVideoAbrMasterCli {

    data class Request(
        val outputPrefix: String,
        val variantsJson: String
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true,
        val masterPath: String?,
        val failReason: String?
    )
}
