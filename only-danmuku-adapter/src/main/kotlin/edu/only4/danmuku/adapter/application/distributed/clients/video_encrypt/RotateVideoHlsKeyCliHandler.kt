package edu.only4.danmuku.adapter.application.distributed.clients.video_encrypt

import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.video_encrypt.RotateVideoHlsKeyCli

import org.springframework.stereotype.Service

/**
 * 防腐层：触发新密钥生成与 m3u8 重写（可重用 EncryptHlsWithKey），用于密钥轮换
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class RotateVideoHlsKeyCliHandler : RequestHandler<RotateVideoHlsKeyCli.Request, RotateVideoHlsKeyCli.Response> {
    override fun exec(request: RotateVideoHlsKeyCli.Request): RotateVideoHlsKeyCli.Response {
        return RotateVideoHlsKeyCli.Response(
            newKeyVersion = TODO("set newKeyVersion"),
            failReason = TODO("set failReason")
        )
    }
}

