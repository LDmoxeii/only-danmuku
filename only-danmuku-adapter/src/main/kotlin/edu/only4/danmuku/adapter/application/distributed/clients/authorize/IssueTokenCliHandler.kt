package edu.only4.danmuku.adapter.application.distributed.clients.authorize

import cn.dev33.satoken.stp.StpUtil
import com.only.engine.entity.UserInfo
import com.only.engine.satoken.utils.LoginHelper
import com.only4.cap4k.ddd.core.application.RequestHandler

import edu.only4.danmuku.application.distributed.clients.authorize.IssueTokenCli

import org.springframework.stereotype.Service

/**
 * 签发 Token
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
@Service
class IssueTokenCliHandler : RequestHandler<IssueTokenCli.Request, IssueTokenCli.Response> {
    override fun exec(request: IssueTokenCli.Request): IssueTokenCli.Response {
        LoginHelper.login(UserInfo(
            request.userId, request.accountType, request.account,
            extra = request.extra
        ))
        val token = StpUtil.getTokenValue()
        return IssueTokenCli.Response(
            token = token
        )
    }
}

