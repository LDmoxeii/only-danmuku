package edu.only4.danmuku.adapter.application.distributed.clients.video_storage

import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.video_storage.UploadVideoAbrOutputCli

import org.springframework.stereotype.Service

/**
 * 上传 ABR 输出目录到 OSS（含 master/variants/segments）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
@Service
class UploadVideoAbrOutputCliHandler : RequestHandler<UploadVideoAbrOutputCli.Request, UploadVideoAbrOutputCli.Response> {
    override fun exec(request: UploadVideoAbrOutputCli.Request): UploadVideoAbrOutputCli.Response {
        return UploadVideoAbrOutputCli.Response(
            success = TODO("set success"),
            storagePrefix = TODO("set storagePrefix"),
            encPrefix = TODO("set encPrefix"),
            failReason = TODO("set failReason")
        )
    }
}

