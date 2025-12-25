package edu.only4.danmuku.application.distributed.clients.video_encrypt

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 防腐层：按清晰度使用独立 key 加密 ABR 输出并生成 enc/master
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
object EncryptHlsWithQualityKeysCli {

    data class Request(
        val sourceDir: String,
        val outputDir: String,
        val keysJson: String,
        val segmentExt: String = ".ts.enc"
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true,
        val encryptedMasterPath: String,
        val encryptedVariants: String,
        val failReason: String?
    )
}
