package edu.only4.danmuku.adapter.application.distributed.clients.oss_storage

import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.oss_storage.OssUploadObjectCli

import org.springframework.stereotype.Service

/**
 * 上传单对象到 OSS
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
@Service
class OssUploadObjectCliHandler : RequestHandler<OssUploadObjectCli.Request, OssUploadObjectCli.Response> {
    override fun exec(request: OssUploadObjectCli.Request): OssUploadObjectCli.Response {
        return OssUploadObjectCli.Response(
            url = TODO("set url"),
            eTag = TODO("set eTag"),
            size = TODO("set size")
        )
    }
}

