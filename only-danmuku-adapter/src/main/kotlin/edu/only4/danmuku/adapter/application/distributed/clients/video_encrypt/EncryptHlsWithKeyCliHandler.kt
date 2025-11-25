package edu.only4.danmuku.adapter.application.distributed.clients.video_encrypt

import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.video_encrypt.EncryptHlsWithKeyCli

import org.springframework.stereotype.Service

/**
 * 防腐层：使用指定 key 对已有 ABR 产物进行 HLS AES-128 加密，产出 enc/ 目录
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class EncryptHlsWithKeyCliHandler : RequestHandler<EncryptHlsWithKeyCli.Request, EncryptHlsWithKeyCli.Response> {
    override fun exec(request: EncryptHlsWithKeyCli.Request): EncryptHlsWithKeyCli.Response {
        return EncryptHlsWithKeyCli.Response(
            success = TODO("set success"),
            encryptedMasterPath = TODO("set encryptedMasterPath"),
            encryptedVariants = TODO("set encryptedVariants"),
            failReason = TODO("set failReason")
        )
    }
}

