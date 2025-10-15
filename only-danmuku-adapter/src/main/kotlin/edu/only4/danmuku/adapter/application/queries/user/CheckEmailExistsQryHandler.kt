package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.user.CheckEmailExistsQry

import org.springframework.stereotype.Service

/**
 * 检查邮箱是否存在
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class CheckEmailExistsQryHandler(
) : Query<CheckEmailExistsQry.Request, CheckEmailExistsQry.Response> {

    override fun exec(request: CheckEmailExistsQry.Request): CheckEmailExistsQry.Response {

        return CheckEmailExistsQry.Response(

        )
    }
}
