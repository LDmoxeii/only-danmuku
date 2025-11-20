package edu.only4.danmuku.adapter.application.distributed.clients

import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.ValidateSmsCodeCli

import org.springframework.stereotype.Service

/**
 * 校验短信验证码（包含防刷逻辑）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
@Service
class ValidateSmsCodeCliHandler : RequestHandler<ValidateSmsCodeCli.Request, ValidateSmsCodeCli.Response> {
    override fun exec(request: ValidateSmsCodeCli.Request): ValidateSmsCodeCli.Response {
        // TODO: 实现远程调用逻辑或适配调用
        return ValidateSmsCodeCli.Response(
        )
    }
}
