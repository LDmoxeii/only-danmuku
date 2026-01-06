package edu.only4.danmuku.application.distributed.clients.video_encrypt

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 防腐层：按档位列表生成加密 master.m3u8
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
object GenerateEncryptedMasterByVariantsCli {

    data class Request(
        val outputDir: String,
        val variantsJson: String
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true,
        val masterPath: String?,
        val failReason: String?
    )
}
