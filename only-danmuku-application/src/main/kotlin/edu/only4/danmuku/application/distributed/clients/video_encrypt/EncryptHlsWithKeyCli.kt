package edu.only4.danmuku.application.distributed.clients.video_encrypt

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 防腐层：使用指定 key 对已有 ABR 产物进行 HLS AES-128 加密，产出 enc/ 目录
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object EncryptHlsWithKeyCli {

    data class Request(
        val sourceDir: String,
        val outputDir: String,
        val keyId: String,
        val quality: String?,
        val keyPlainHex: String,
        val ivHex: String?,
        val segmentExt: String = .ts.enc
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean = true,
        val encryptedMasterPath: String,
        val encryptedVariants: String,
        val failReason: String?
    )
}
