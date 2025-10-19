package edu.only4.danmuku.adapter.application.queries.user

import com.only4.cap4k.ddd.core.application.query.Query

import edu.only4.danmuku.application.queries.user.GetAccountInfoByEmailQry

import org.springframework.stereotype.Service

/**
 *
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/19
 */
@Service
class GetAccountInfoByEmailQryHandler(
) : Query<GetAccountInfoByEmailQry.Request, GetAccountInfoByEmailQry.Response> {

    override fun exec(request: GetAccountInfoByEmailQry.Request): GetAccountInfoByEmailQry.Response {

        return GetAccountInfoByEmailQry.Response(

        )
    }
}
