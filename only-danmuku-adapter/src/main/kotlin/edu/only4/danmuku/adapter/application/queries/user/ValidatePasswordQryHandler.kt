package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.user.ValidatePasswordQry

import org.springframework.stereotype.Service

/**
 * 验证密码
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
class ValidatePasswordQryHandler(
) : Query<ValidatePasswordQry.Request, ValidatePasswordQry.Response> {

    override fun exec(request: ValidatePasswordQry.Request): ValidatePasswordQry.Response {

        return ValidatePasswordQry.Response(

        )
    }
}
