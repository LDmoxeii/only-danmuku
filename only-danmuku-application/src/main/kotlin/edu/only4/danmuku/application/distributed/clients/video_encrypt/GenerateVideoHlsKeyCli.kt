package edu.only4.danmuku.application.distributed.clients.video_encrypt

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 防腐层：向 KMS 申请随机 AES-128 key + IV，生成 keyId/keyUri 占位
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object GenerateVideoHlsKeyCli {

    data class Request(
        val quality: String?,
        val method: String = HLS_AES_128,
        val keyBytes: Int = 16
    ) : RequestParam<Response>

    data class Response(
        val keyId: String,
        val keyCiphertextBase64: String,
        val ivHex: String?,
        val keyVersion: Int = 1,
        val keyUriTemplate: String
    )
}
