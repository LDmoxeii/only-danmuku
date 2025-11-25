package edu.only4.danmuku.application.distributed.clients

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 防腐层：对单个上传文件执行转码，返回成功/失败、输出路径、时长
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Deprecated("已被 TranscodeVideoFileToAbrCli 替代，多路转码使用新 CLI")
object TranscodeVideoFileCli {

    data class Request(
        val videoFilePostId: Long,
        val uploadId: Long,
        val customerId: Long,
        val videoId: Long,
        val fileIndex: Int,
        val fileName: String?,
        val tempPath: String? = null,
    ) : RequestParam<Response>

    data class Response(
        val success: Boolean,
        val outputPath: String? = null,
        val duration: Int? = null,
        val fileSize: Long? = null,
        val failReason: String? = null,
    )
}
