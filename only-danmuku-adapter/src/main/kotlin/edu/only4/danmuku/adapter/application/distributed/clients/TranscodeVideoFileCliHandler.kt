package edu.only4.danmuku.adapter.application.distributed.clients

import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.TranscodeVideoFileCli

import org.springframework.stereotype.Service

/**
 * 防腐层：对单个上传文件执行转码，返回成功/失败、输出路径、时长
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Service
class TranscodeVideoFileCliHandler : RequestHandler<TranscodeVideoFileCli.Request, TranscodeVideoFileCli.Response> {
    override fun exec(request: TranscodeVideoFileCli.Request): TranscodeVideoFileCli.Response {
        // TODO: 实现远程调用逻辑或适配调用
        return TranscodeVideoFileCli.Response(
        )
    }
}
