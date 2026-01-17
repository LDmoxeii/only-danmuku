package edu.only4.danmuku.adapter.application.distributed.clients.authorize

import cn.dev33.satoken.stp.StpUtil
import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.authorize.LogoutCli

import org.springframework.stereotype.Service

/**
 * 用户登出
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
@Service
class LogoutCliHandler : RequestHandler<LogoutCli.Request, LogoutCli.Response> {
    override fun exec(request: LogoutCli.Request): LogoutCli.Response {
        StpUtil.logout()
        return LogoutCli.Response()
    }
}

