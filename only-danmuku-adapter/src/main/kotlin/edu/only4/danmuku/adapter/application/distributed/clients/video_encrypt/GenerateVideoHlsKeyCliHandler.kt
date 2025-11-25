package edu.only4.danmuku.adapter.application.distributed.clients.video_encrypt

import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.video_encrypt.GenerateVideoHlsKeyCli

import org.springframework.stereotype.Service

/**
 * 防腐层：向 KMS 申请随机 AES-128 key + IV，生成 keyId/keyUri 占位
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
class GenerateVideoHlsKeyCliHandler : RequestHandler<GenerateVideoHlsKeyCli.Request, GenerateVideoHlsKeyCli.Response> {
    override fun exec(request: GenerateVideoHlsKeyCli.Request): GenerateVideoHlsKeyCli.Response {
        return GenerateVideoHlsKeyCli.Response(
            keyId = TODO("set keyId"),
            keyCiphertextBase64 = TODO("set keyCiphertextBase64"),
            ivHex = TODO("set ivHex"),
            keyVersion = TODO("set keyVersion"),
            keyUriTemplate = TODO("set keyUriTemplate")
        )
    }
}

