package edu.only4.danmuku.application.distributed.clients.file_upload_session

import com.only4.cap4k.ddd.core.application.RequestParam

import org.springframework.web.multipart.MultipartFile

/**
 * 保存视频上传分片到临时存储（当前本地实现，可扩展为 OSS）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
object UploadVideoChunkStorageCli {

    data class Request(
        val tempPath: String,
        val chunkIndex: Int,
        val chunkFile: MultipartFile
    ) : RequestParam<Response>

    data class Response(
        val storedPath: String,
        val size: Long
    )
}
