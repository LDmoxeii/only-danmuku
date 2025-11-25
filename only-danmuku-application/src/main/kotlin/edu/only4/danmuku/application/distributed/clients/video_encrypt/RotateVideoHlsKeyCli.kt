package edu.only4.danmuku.application.distributed.clients.video_encrypt

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 防腐层：触发新密钥生成与 m3u8 重写（可重用 EncryptHlsWithKey），用于密钥轮换
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object RotateVideoHlsKeyCli {

    data class Request(
        val reason: String?
    ) : RequestParam<Response>

    data class Response(
        val newKeyVersion: Int,
        val failReason: String?
    )
}
