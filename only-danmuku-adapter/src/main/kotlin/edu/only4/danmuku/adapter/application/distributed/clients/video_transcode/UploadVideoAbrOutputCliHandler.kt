package edu.only4.danmuku.adapter.application.distributed.clients.video_transcode

import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.video_transcode.UploadVideoAbrOutputCli

import org.springframework.stereotype.Service

/**
 * 防腐层：上传 ABR 输出目录到对象存储（不接触数据库ID）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@Service
class UploadVideoAbrOutputCliHandler : RequestHandler<UploadVideoAbrOutputCli.Request, UploadVideoAbrOutputCli.Response> {
    override fun exec(request: UploadVideoAbrOutputCli.Request): UploadVideoAbrOutputCli.Response {
        return UploadVideoAbrOutputCli.Response(
            success = TODO("set success"),
            storagePrefix = TODO("set storagePrefix"),
            failReason = TODO("set failReason")
        )
    }
}

