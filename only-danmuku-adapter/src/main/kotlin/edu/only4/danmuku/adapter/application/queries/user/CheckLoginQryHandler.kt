package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.user.CheckLoginQry

import org.springframework.stereotype.Service

/**
 * 检查登录
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class CheckLoginQryHandler(
) : Query<CheckLoginQry.Request, CheckLoginQry.Response> {

    override fun exec(request: CheckLoginQry.Request): CheckLoginQry.Response {

        return TODO()
    }
}
