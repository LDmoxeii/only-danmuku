package edu.only4.danmuku.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 查询稿件文件的加密状态及最新 key 信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object GetVideoEncryptStatusQry {

    data class Request(
        val fileId: Long,
        val videoFileId: Long?
    ) : RequestParam<Response>

    data class Response(
        val encryptStatus: Int,
        val encryptMethod: String?,
        val keyId: String?,
        val keyVersion: Int?,
        val keyQuality: String?,
        val encryptedMasterPath: String?
    )
}
