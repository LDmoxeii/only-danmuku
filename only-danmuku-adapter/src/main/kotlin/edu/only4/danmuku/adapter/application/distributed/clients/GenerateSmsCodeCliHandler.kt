package edu.only4.danmuku.adapter.application.distributed.clients

import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.GenerateSmsCodeCli

import org.springframework.stereotype.Service

/**
 * 生成并下发短信验证码（带场景）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
@Service
class GenerateSmsCodeCliHandler : RequestHandler<GenerateSmsCodeCli.Request, GenerateSmsCodeCli.Response> {
    override fun exec(request: GenerateSmsCodeCli.Request): GenerateSmsCodeCli.Response {
        // TODO: 实现远程调用逻辑或适配调用
        return GenerateSmsCodeCli.Response(
        )
    }
}
