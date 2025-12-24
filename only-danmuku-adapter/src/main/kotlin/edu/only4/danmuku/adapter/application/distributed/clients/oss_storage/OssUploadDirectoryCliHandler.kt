package edu.only4.danmuku.adapter.application.distributed.clients.oss_storage

import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.oss_storage.OssUploadDirectoryCli

import org.springframework.stereotype.Service

/**
 * 上传目录到 OSS（用于 HLS 输出）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
@Service
class OssUploadDirectoryCliHandler : RequestHandler<OssUploadDirectoryCli.Request, OssUploadDirectoryCli.Response> {
    override fun exec(request: OssUploadDirectoryCli.Request): OssUploadDirectoryCli.Response {
        return OssUploadDirectoryCli.Response(
            uploadedCount = TODO("set uploadedCount"),
            prefix = TODO("set prefix")
        )
    }
}

